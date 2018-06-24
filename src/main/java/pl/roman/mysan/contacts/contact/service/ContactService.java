package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.common.DuplicateValidator;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;
import pl.roman.mysan.contacts.exceptions.NotFoundException;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ValidationService.validatePersonContacts;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final PersonRepository personRepository;
    private final DuplicateValidator duplicateValidator;

    public void addContacts(Long id, PersonContactDTO contactsDto) {
        validatePersonContacts(contactsDto.getEmails(), contactsDto.getPhones());
        duplicateValidator.validateDuplicates(contactsDto.getPhones(), contactsDto.getEmails());
        if (personRepository.existsById(id)) {
            Person person = personRepository.getOne(id);
            List<Contact> contacts = convertContacts(contactsDto.getEmails(), contactsDto.getPhones(), person);
            save(person, contacts);
        } else
            throw new NotFoundException("Person with id=" + id + "does not exist!");
    }

    private void save(Person person, List<Contact> contact) {
        List<Contact> contacts = person.getContacts();
        contacts.addAll(contact);
        person.setContacts(contacts);
        personRepository.save(person);
    }

    public void edit(ContactDTO contactDTO) {
        if (contactRepository.existsById(contactDTO.getId())) {
            Contact contact = contactRepository.getOne(contactDTO.getId());
            if (contact instanceof PhoneNumber) {
                duplicateValidator.validateDuplicates(Arrays.asList(((PhoneNumberDTO) contactDTO)), Collections.emptyList());
                validatePersonContacts(Collections.emptyList(), Arrays.asList(((PhoneNumberDTO) contactDTO)));
            } else {
                duplicateValidator.validateDuplicates(Collections.emptyList(), Arrays.asList(((EmailAddressDTO) contactDTO)));
                validatePersonContacts(Arrays.asList(((EmailAddressDTO) contactDTO)), Collections.emptyList());
            }
            contact.edit(contactDTO);
            contactRepository.saveAndFlush(contact);
        } else
            throw new NotFoundException("Contact with id=" + contactDTO.getId() + " does not exist!");
    }

    public void delete(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
        } else
            throw new NotFoundException("Contact with id=" + id + " does not exist!");
    }

    private static List<Contact> convertContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones, Person person) {
        return ContactAsm.collectContacts(emails, phones)
                .stream()
                .map(c -> ContactAsm.createEntityObject(c, person))
                .collect(Collectors.toList());
    }
}
