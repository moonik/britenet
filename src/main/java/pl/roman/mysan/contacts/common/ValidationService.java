package pl.roman.mysan.contacts.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.exceptions.ValidationException;
import pl.roman.mysan.contacts.person.model.PersonDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ApplicationConstants.EMAIL_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_DATE_FORMAT;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_EMAIL_ADRESS;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_NAME_OR_SURNAME;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_PESEL;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_PHONE;
import static pl.roman.mysan.contacts.common.ApplicationConstants.PESEL_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.PHONE_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.VALID_PAST_DATE;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationService {

    public static void validatePersonData(PersonDTO personDTO) {
        LocalDate date = personDTO.getBirthDate();
        LocalDate past = LocalDate.parse(VALID_PAST_DATE);
        StringBuilder str = new StringBuilder();
        if (!date.isAfter(past) || !date.isBefore(LocalDate.now().plusDays(1))) {
            str.append(INVALID_DATE_FORMAT);
        }
        if (personDTO.getName().isEmpty() || personDTO.getSurname().isEmpty()) {
            str.append(INVALID_NAME_OR_SURNAME);
        }
        if (!personDTO.getPesel().matches(PESEL_PATTERN)) {
            str.append(INVALID_PESEL);
        }
        String invalidContacts = validateContacts(personDTO.getContacts().getEmails(), personDTO.getContacts().getPhones());
        if (!invalidContacts.isEmpty()) {
            str.append(invalidContacts);
        }
        if (!str.toString().trim().isEmpty()) {
            throw new ValidationException(str.toString().trim());
        }
    }

    private static String validateContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones) {
        return new StringBuilder()
                .append(validateEmails(emails))
                .append("\n")
                .append(validatePhones(phones))
                .toString();
    }

    public static void validatePersonContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones) {
        StringBuilder str = new StringBuilder();
        str.append(validateEmails(emails) + "\n");
        str.append(validatePhones(phones));
        if (!str.toString().trim().isEmpty()) {
            throw new ValidationException(str.toString().trim());
        }
    }

    private static String validateEmails(List<EmailAddressDTO> emails) {
        return ValidationService.validateByPattern(
                emails.stream()
                        .map(EmailAddressDTO::getEmail)
                        .collect(Collectors.toList()),
                EMAIL_PATTERN, INVALID_EMAIL_ADRESS);
    }

    private static String validatePhones(List<PhoneNumberDTO> phones) {
        return ValidationService.validateByPattern(
                phones.stream()
                        .map(PhoneNumberDTO::getPhone)
                        .collect(Collectors.toList()),
                PHONE_PATTERN, INVALID_PHONE);
    }

    private static String validateByPattern(List<String> contacts, String pattern, String message) {
        String invalid = contacts.stream().filter(c -> !c.matches(pattern)).collect(Collectors.joining(","));
        if (!invalid.isEmpty()) {
           return message + invalid;
        } else
            return "";
    }
}
