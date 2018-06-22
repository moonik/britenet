package pl.roman.mysan.contacts;

import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataFactory {

    private static final Long ID = 1L;
    private static final String EMPTY = "";
    private static final String NAME = "Roman";
    private static final String SURNAME = "Mysan";
    private static final Character GENDER = 'M';
    private static final LocalDate[] birthDates = {LocalDate.parse("1995-08-13"), LocalDate.parse("1917-12-12"), LocalDate.parse("2020-03-05")};
    private static final String[] pesels = {"12345678901", "12dasd", "12345678900"};
    private static final String[] emails = {"roman@gmail.com", "romangmail.com", "roman@.com", "roman@com", "roman@gmail", "roman"};
    private static final String[] phones = {"123456789", "1", "32193879387319783", "321987dkjalskdja", "312dasd213"};

    public static PersonDTO personDtoWithInvalidPastDate() {
        return PersonDTO.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[1])
                .pesel(pesels[0])
                .contacts(personContactDtoWithValidData())
                .build();
    }

    public static PersonDTO personDtoWithFutureDate() {
        return PersonDTO.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[2])
                .pesel(pesels[0])
                .contacts(personContactDtoWithValidData())
                .build();
    }

    public static PersonDTO personDtoWithDuplicatedPesel() {
        return PersonDTO.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[2])
                .pesel(pesels[2])
                .build();
    }

    public static PersonDTO personDtoWithInvalidData() {
        return PersonDTO.builder()
                .id(ID)
                .name(EMPTY)
                .surname(EMPTY)
                .gender(GENDER)
                .birthDate(birthDates[2])
                .pesel(pesels[1])
                .contacts(personContactDtoWithInvalidData())
                .build();
    }

    public static PersonDTO personDtoWithValidData() {
        return PersonDTO.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[0])
                .pesel(pesels[0])
                .contacts(personContactDtoWithValidData())
                .build();
    }

    public static Person personWithContacts() {
        Person person = buildPerson();
        List<Contact> contacts = new ArrayList<>(
                Arrays.asList(new EmailAddress(person, emails[0]), new PhoneNumber(person, phones[0])));
        person.setContacts(contacts);
        return person;
    }

    private static Person buildPerson() {
        return Person.builder()
                .id(ID)
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[0])
                .pesel(pesels[0])
                .build();
    }

    public static PersonContactDTO personContactDtoWithInvalidData() {
        return new PersonContactDTO(createEmailAddressesDto(), createPhoneNumbersDto());
    }

    public static PersonContactDTO personContactDtoWithValidData() {
        return new PersonContactDTO(
                Arrays.asList(new EmailAddressDTO(ID, emails[0])),
                Arrays.asList(new PhoneNumberDTO(ID, phones[0])));
    }

    private static List<EmailAddressDTO> createEmailAddressesDto() {
        return Arrays.stream(emails)
                .map(e -> new EmailAddressDTO(ID, e))
                .collect(Collectors.toList());
    }

    private static List<PhoneNumberDTO> createPhoneNumbersDto() {
        return Arrays.stream(phones)
                .map(p -> new PhoneNumberDTO(ID, p))
                .collect(Collectors.toList());
    }
}
