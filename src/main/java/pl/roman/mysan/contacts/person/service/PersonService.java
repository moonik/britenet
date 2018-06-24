package pl.roman.mysan.contacts.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.common.DuplicateValidator;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;
import pl.roman.mysan.contacts.exceptions.NotFoundException;
import pl.roman.mysan.contacts.person.asm.PersonAsm;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ApplicationConstants.EMAIL_PATTERN;
import static pl.roman.mysan.contacts.common.ValidationService.validatePersonData;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final DuplicateValidator duplicateValidator;

    public void save(PersonDTO personDTO) {
        if (personRepository.findByPesel(personDTO.getPesel()) != null) {
            throw new AlreadyExistsException("Person with pesel " + personDTO.getPesel() + "already exist!");
        }
        validatePersonData(personDTO);
        duplicateValidator.validateDuplicates(personDTO.getContacts().getPhones(), personDTO.getContacts().getEmails());
        Person person = PersonAsm.createEntityObject(personDTO);
        List<Contact> contacts = convertContacts(personDTO.getContacts(), person);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public void edit(PersonDTO personDTO) {
        validatePersonData(personDTO);
        if (personRepository.existsById(personDTO.getId())) {
            Person person = personRepository.getOne(personDTO.getId());
            person.edit(personDTO);
            personRepository.saveAndFlush(person);
        } else
            throw new NotFoundException("Person with id=" + personDTO.getId() + " does not exist!");
    }

    public void delete(Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
        } else
            throw new NotFoundException("Person with id=" + id + " does not exist!");
    }

    public List<PersonInfoDTO> findPeopleByBirthDateBetween(LocalDate first, LocalDate second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("One of the date is empty!");
        }
        return personRepository.findByBirthDateBetween(first, second)
                .stream()
                .map(PersonAsm::createPersonInfoDto)
                .collect(Collectors.toList());
    }

    public List<PersonInfoDTO> findPeopleByEmail(String email) {
        if (email.contains("*")) {
            return findByPattern("@" + email.replaceAll("\\*", ""));
        } else
            return findByEmail(email);
    }

    private List<PersonInfoDTO> findByEmail(String email) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Invalid email!");
        }
        return personRepository.findPeopleByEmail(email)
                .stream()
                .map(PersonAsm::createPersonInfoDto)
                .collect(Collectors.toList());
    }

    private List<PersonInfoDTO> findByPattern(String pattern) {
        return personRepository.findPeopleByPattern(pattern)
                .stream()
                .map(PersonAsm::createPersonInfoDto)
                .collect(Collectors.toList());
    }

    private static List<Contact> convertContacts(PersonContactDTO contacts, Person person) {
        List<EmailAddressDTO> emails = contacts.getEmails();
        List<PhoneNumberDTO> phones = contacts.getPhones();
        return ContactAsm.collectContacts(emails, phones)
                .stream()
                .map(c -> ContactAsm.createEntityObject(c, person))
                .collect(Collectors.toList());
    }
}
