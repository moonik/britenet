package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.common.ValidationService;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
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

    public void addContacts(Long id, List<ContactDTO> contactsDto) {
        Person person = personRepository.getOne(id);
        List<Contact> contacts = contactsDto.stream().map(c -> ContactAsm.createEntityObject(c, person)).collect(Collectors.toList());
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
        contact.edit(contactDTO);
        contactRepository.saveAndFlush(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
