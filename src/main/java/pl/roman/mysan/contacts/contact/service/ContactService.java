package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ValidationService.validateEmails;
import static pl.roman.mysan.contacts.common.ValidationService.validatePhones;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;

    public void addContacts(Long id, PersonContactDTO contactsDto) {
        validateEmails(contactsDto.getEmails());
        validatePhones(contactsDto.getPhones());
        Person person = personRepository.getOne(id);
        List<Contact> contacts = convertContacts(contactsDto.getEmails(), contactsDto.getPhones(), person);
        save(person, contacts);
    }

    private void save(Person person, List<Contact> contact) {
        List<Contact> contacts = person.getContacts();
        contacts.addAll(contact);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public void edit(ContactDTO contactDTO) {
        Contact contact = contactRepository.getOne(contactDTO.getId());
        if (contact instanceof PhoneNumber) {
            validatePhones(Arrays.asList(((PhoneNumberDTO) contactDTO)));
        } else
            validateEmails(Arrays.asList(((EmailAddressDTO) contactDTO)));
        contact.edit(contactDTO);
        contactRepository.saveAndFlush(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    private static List<Contact> convertContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones, Person person) {
        return ContactAsm.collectContacts(emails, phones)
                .stream()
                .map(c -> ContactAsm.createEntityObject(c, person))
                .collect(Collectors.toList());
    }
}
