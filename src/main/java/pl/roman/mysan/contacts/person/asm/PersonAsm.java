package pl.roman.mysan.contacts.person.asm;

import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PersonAsm {

    public static Person createEntityObject(PersonDTO personDTO) {
        return Person.builder()
                .name(personDTO.getName())
                .surname(personDTO.getSurname())
                .gender(personDTO.getGender())
                .birthDate(LocalDate.parse(personDTO.getBirthDate()))
                .pesel(personDTO.getPesel())
                .build();
    }

    public static PersonInfoDTO createPersonInfoDto(Person person) {
        return PersonInfoDTO.builder()
                .name(person.getName())
                .surname(person.getSurname())
                .gender(person.getGender())
                .birthDate(person.getBirthDate().toString())
                .pesel(person.getPesel())
                .contacts(convertContactsToDto(person.getContacts()))
                .build();
    }

    private static List<ContactDTO> convertContactsToDto(List<Contact> contacts) {
        return contacts.stream()
                .map(ContactAsm::createDtoObject)
                .collect(Collectors.toList());
    }
}
