package pl.roman.mysan.contacts.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;
import pl.roman.mysan.contacts.person.asm.PersonAsm;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ValidationService.validateEmails;
import static pl.roman.mysan.contacts.common.ValidationService.validatePhones;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void save(PersonDTO personDTO) {
        if (personRepository.findByPesel(personDTO.getPesel()) != null) {
            throw new AlreadyExistsException("Person with pesel " + personDTO.getPesel() + "already exist!");
        }
        Person person = PersonAsm.createEntityObject(personDTO);
        validateContacts(personDTO.getContacts());
        List<Contact> contacts = convertContacts(personDTO.getContacts(), person);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public void edit(PersonDTO personDTO) {
        Person person = personRepository.getOne(personDTO.getId());
        person.edit(personDTO);
        personRepository.saveAndFlush(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public List<PersonInfoDTO> findPeopleByBirthDateBetween(String first, String second) {
        return personRepository.findByBirthDateBetween(LocalDate.parse(first), LocalDate.parse(second))
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

    private static void validateContacts(PersonContactDTO contacts) {
        validateEmails(contacts.getEmails());
        validatePhones(contacts.getPhones());
    }
}
