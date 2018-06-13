package pl.roman.mysan.contacts.person.asm;

import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PersonAsm {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-mm-yyyy");

    public static Person createEntityObject(PersonDTO personDTO, List<Contact> contacts) {
        return Person.builder()
                .name(personDTO.getName())
                .surname(personDTO.getSurname())
                .gender(personDTO.getGender())
                .birthDate(LocalDate.parse(personDTO.getBirthDate()))
                .pesel(personDTO.getPesel())
                .contacts(contacts)
                .build();
    }

    public static PersonInfoDTO createDtoObject(Person person, List<ContactDTO> contacts) {
        return PersonInfoDTO.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .gender(person.getGender())
                .birthDate(formatter.format(person.getBirthDate()))
                .pesel(person.getPesel())
                .contacts(contacts)
                .build();
    }
}
