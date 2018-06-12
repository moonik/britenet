package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    public void addNew(Long id, ContactDTO contactDTO) {
        Person person = personRepository.getOne(id);
        Contact contact = ContactAsm.createEntityObject(contactDTO);
        List<Contact> contacts = person.getContacts();
        contacts.add(contact);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public List<ContactDTO> getAll(Long id) {
        return personRepository.getOne(id).getContacts().stream()
                .map(ContactAsm::createDtoObject)
                .collect(Collectors.toList());
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
