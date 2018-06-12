package pl.roman.mysan.contacts.person.asm;

import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PersonAssembler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");

    public static Person createEntityObject(PersonDTO personDTO) {
        return Person.builder()
                .name(personDTO.getName())
                .surname(personDTO.getSurname())
                .gender(personDTO.getGender())
                .birthDate(LocalDate.parse(personDTO.getBirthDate()))
                .pesel(personDTO.getPesel())
                .contacts(convertContactsToEntity(personDTO.getContacts()))
                .build();
    }

    public static PersonDTO createDtoObject(Person person) {
        return PersonDTO.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .gender(person.getGender())
                .birthDate(formatter.format(person.getBirthDate()))
                .pesel(person.getPesel())
                .contacts(convertContactsToDto(person.getContacts()))
                .build();
    }

    private static List<Contact> convertContactsToEntity(List<ContactDTO> contacts) {
        return contacts.stream()
                .map(ContactAsm::createEntityObject)
                .collect(Collectors.toList());
    }

    private static List<ContactDTO> convertContactsToDto(List<Contact> contacts) {
        return contacts.stream()
                .map(ContactAsm::createDtoObject)
                .collect(Collectors.toList());
    }
}
