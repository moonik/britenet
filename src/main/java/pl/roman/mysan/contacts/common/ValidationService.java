package pl.roman.mysan.contacts.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.exceptions.ValidationException;
import pl.roman.mysan.contacts.person.model.PersonDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ApplicationConstants.EMAIL_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_FORMAT_FOR_EMAIL_ADRESS;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_FORMAT_FOR_PHONE;
import static pl.roman.mysan.contacts.common.ApplicationConstants.PESEL_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.PHONE_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.VALID_PAST_DATE;

@Service
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationService {

    public static void validatePersonData(PersonDTO personDTO) {
        LocalDate date = personDTO.getBirthDate();
        LocalDate past = LocalDate.parse(VALID_PAST_DATE);
        StringBuilder str = new StringBuilder();
        if (!date.isAfter(past) || !date.isBefore(LocalDate.now().plusDays(1))) {
            str.append("Date can't be future or lower than 1918-01-01!\n");
        }
        if (personDTO.getName().isEmpty() || personDTO.getSurname().isEmpty()) {
            str.append("Fields name and surname can't be empty!\n");
        }
        if (!personDTO.getPesel().matches(PESEL_PATTERN)) {
            str.append("Pesel should contains 11 digits!\n");
        }
        str.append(validateEmails(personDTO.getContacts().getEmails()) + "\n");
        str.append(validatePhones(personDTO.getContacts().getPhones()) + "\n");
        if (!str.toString().isEmpty()) {
            throw new ValidationException(str.toString());
        }
    }

    public static void validateContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones) {
        StringBuilder str = new StringBuilder();
        str.append(validateEmails(emails));
        str.append(validatePhones(phones));
    }

    private static String validateEmails(List<EmailAddressDTO> emails) {
        return ValidationService.validateByPattern(
                emails.stream()
                        .map(EmailAddressDTO::getEmail)
                        .collect(Collectors.toList()),
                EMAIL_PATTERN, INVALID_FORMAT_FOR_EMAIL_ADRESS);
    }

    private static String validatePhones(List<PhoneNumberDTO> phones) {
        return ValidationService.validateByPattern(
                phones.stream()
                        .map(PhoneNumberDTO::getPhone)
                        .collect(Collectors.toList()),
                PHONE_PATTERN, INVALID_FORMAT_FOR_PHONE);
    }

    private static String validateByPattern(List<String> contacts, String pattern, String message) {
        String invalid = contacts.stream().filter(c -> !c.matches(pattern)).collect(Collectors.joining(","));
        if (!invalid.isEmpty()) {
           return message + invalid;
        } else
            return "";
    }
}
