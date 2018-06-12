package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public void edit(ContactDTO contactDTO) {
        Contact contact = contactRepository.getOne(contactDTO.getId());
        contact.edit(contactDTO);
        contactRepository.saveAndFlush(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
