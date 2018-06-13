package pl.roman.mysan.contacts.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.person.asm.PersonAsm;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void save(PersonDTO personDTO) {
        List<Contact> contacts = new ArrayList<>();
        contacts.addAll(personDTO.getEmails().stream().map(ContactAsm::createEntityObject)
                .collect(Collectors.toList()));
        contacts.addAll(personDTO.getPhoneNubers().stream().map(ContactAsm::createEntityObject)
                .collect(Collectors.toList()));
        personRepository.save(PersonAsm.createEntityObject(personDTO, contacts));
    }

    public void edit(Long id, PersonDTO personDTO) {
        Person person = personRepository.getOne(id);
        person.edit(personDTO);
        personRepository.saveAndFlush(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
