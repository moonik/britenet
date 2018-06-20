package pl.roman.mysan.contacts.common;

import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.exceptions.InvalidFormatException;

import java.util.List;
import java.util.stream.Collectors;

import static pl.roman.mysan.contacts.common.ApplicationConstants.EMAIL_PATTERN;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_FORMAT_FOR_EMAIL_ADRESS;
import static pl.roman.mysan.contacts.common.ApplicationConstants.INVALID_FORMAT_FOR_PHONE;
import static pl.roman.mysan.contacts.common.ApplicationConstants.PHONE_PATTERN;

@Service
public class ValidationService {

    public static void validateEmails(List<EmailAddressDTO> emails) {
        ValidationService.validateByPattern(
                emails.stream()
                        .map(EmailAddressDTO::getEmail)
                        .collect(Collectors.toList()),
                EMAIL_PATTERN, INVALID_FORMAT_FOR_EMAIL_ADRESS);
    }

    public static void validatePhones(List<PhoneNumberDTO> phones) {
        ValidationService.validateByPattern(
                phones.stream()
                        .map(PhoneNumberDTO::getPhone)
                        .collect(Collectors.toList()),
                PHONE_PATTERN, INVALID_FORMAT_FOR_PHONE);
    }

    private static void validateByPattern(List<String> contacts, String pattern, String message) {
        String invalid = contacts.stream().filter(c -> !c.matches(pattern)).collect(Collectors.joining(","));
        if (!invalid.isEmpty()) {
            throw new InvalidFormatException(message + invalid);
        }
    }
}
