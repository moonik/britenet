package pl.roman.mysan.contacts.contact.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.roman.mysan.contacts.common.DuplicateValidator;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;
import pl.roman.mysan.contacts.exceptions.NotFoundException;
import pl.roman.mysan.contacts.exceptions.ValidationException;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ApplicationConstants.EMAIL_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_EMAIL_ADRESS;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_PHONE;
import static pl.roman.mysan.contacts.common.ApplicationConstants.PHONE_PATTERN;
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

    public void edit(Long id, String value) {
        if (contactRepository.existsById(id)) {
            Contact contact = contactRepository.getOne(id);
            if (contact instanceof PhoneNumber) {
                if (!value.matches(PHONE_PATTERN)) {
                    throw new ValidationException(INVALID_PHONE + value);
                }
            } else {
                if (!value.matches(EMAIL_PATTERN)) {
                    throw new ValidationException(INVALID_EMAIL_ADRESS + value);
                }
            }
            contact.edit(value);
            contactRepository.saveAndFlush(contact);
        } else
            throw new NotFoundException("Contact with id=" + id + " does not exist!");
    }

    public void delete(Long contactId, Long personId) {
        if (contactRepository.existsById(contactId)) {
            if (personRepository.existsById(personId)) {
                Person person = personRepository.getOne(personId);
                List<Contact> contacts = person.getContacts();
                Contact contact = contactRepository.getOne(contactId);
                contacts.remove(contact);
                person.setContacts(contacts);
                personRepository.saveAndFlush(person);
                contactRepository.delete(contact);
            } else
                throw new NotFoundException("Person with id=" + personId + " does not exist!");
        } else
            throw new NotFoundException("Contact with id=" + contactId + " does not exist!");
    }

    private static List<Contact> convertContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones, Person person) {
        return ContactAsm.collectContacts(emails, phones)
                .stream()
                .map(c -> ContactAsm.createEntityObject(c, person))
                .collect(Collectors.toList());
    }
}
