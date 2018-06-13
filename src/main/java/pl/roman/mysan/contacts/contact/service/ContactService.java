package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;

    public void addPhones(Long id, List<PhoneNumberDTO> phones) {
        List<Contact> contacts = phones.stream().map(ContactAsm::createEntityObject).collect(Collectors.toList());
        save(id, contacts);
    }

    public void addEmails(Long id, List<EmailAddressDTO> emails) {
        List<Contact> contacts = emails.stream().map(ContactAsm::createEntityObject).collect(Collectors.toList());
        save(id, contacts);
    }

    private void save(Long id, List<Contact> contact) {
        Person person = personRepository.getOne(id);
        List<Contact> contacts = person.getContacts();
        contacts.addAll(contact);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public void edit(ContactDTO contactDTO) {
        Contact contact = contactRepository.getOne(contactDTO.getId());
        contact.edit(contactDTO);
        contactRepository.saveAndFlush(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
