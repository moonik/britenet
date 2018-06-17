package pl.roman.mysan.contacts.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.asm.PersonAsm;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void save(PersonDTO personDTO) {
        Person person = PersonAsm.createEntityObject(personDTO);
        List<Contact> contacts = convertContacts(personDTO.getContacts(), person);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public void edit(Long id, PersonDTO personDTO) {
        Person person = personRepository.getOne(id);
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
        return personRepository.findPeopleByEmail(email)
                .stream()
                .map(PersonAsm::createPersonInfoDto)
                .collect(Collectors.toList());
    }

    public List<PersonInfoDTO> findPeopleByPattern(String pattern) {
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
