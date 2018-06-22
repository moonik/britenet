package pl.roman.mysan.contacts;

import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestDataFactory {

    private static final Long[] IDS = {1L, 2L};
    private static final String NAME = "Roman";
    private static final String SURNAME = "Mysan";
    private static final Character GENDER = 'M';
    private static final LocalDate[] birthDates = {LocalDate.parse("1995-08-13"), LocalDate.parse("1917-12-12"), LocalDate.parse("2020-03-05")};
    private static final String[] pesels = {"12345678901", "11111111111"};
    private static final String[] emails = {"roman@gmail.com", "romangmail.com", "roman@.com", "roman@com", "roman@gmail", "roman"};
    private static final String[] phones = {"123456789", "1", "32193879387319783", "321987dkjalskdja", "312dasd213"};

    public static PersonDTO personDTOWithInvalidPastDate() {
        return PersonDTO.builder()
                .id(IDS[1])
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[1])
                .pesel(pesels[0])
                .build();
    }

    public static PersonDTO personDTOWithInvalidFutureDate() {
        return PersonDTO.builder()
                .id(IDS[1])
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[2])
                .pesel(pesels[0])
                .build();
    }

    public static PersonDTO personDTOWithDuplicatedPesel() {
        return PersonDTO.builder()
                .id(IDS[1])
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[2])
                .pesel(pesels[1])
                .build();
    }

    public static PersonDTO personDTOWithInvalidContacts() {
        PersonContactDTO contacts = new PersonContactDTO(createEmailAdressesDto(), createPhoneNumbersDTO());
        return PersonDTO.builder()
                .id(IDS[1])
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[2])
                .pesel(pesels[1])
                .contacts(contacts)
                .build();
    }

    public static PersonDTO personDTOWithValidFields() {
        return PersonDTO.builder()
                .id(IDS[0])
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[0])
                .pesel(pesels[0])
                .build();
    }

    public static Person buildPerson() {
        return Person.builder()
                .id(IDS[0])
                .name(NAME)
                .surname(SURNAME)
                .gender(GENDER)
                .birthDate(birthDates[0])
                .pesel(pesels[0])
                .build();
    }

    private static List<EmailAddressDTO> createEmailAdressesDto() {
        return Arrays.stream(emails)
                .map(e -> new EmailAddressDTO(IDS[0], e))
                .collect(Collectors.toList());
    }

    private static List<PhoneNumberDTO> createPhoneNumbersDTO() {
        return Arrays.stream(phones)
                .map(p -> new PhoneNumberDTO(IDS[0], p))
                .collect(Collectors.toList());
    }
}
