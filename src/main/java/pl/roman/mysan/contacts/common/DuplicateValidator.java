package pl.roman.mysan.contacts.common;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DuplicateValidator {

    private final ContactRepository contactRepository;

    public void validateDuplicates(List<PhoneNumberDTO> phones, List<EmailAddressDTO> emails) {
        String duplicatePhones = searchPhoneDuplicates(phones);
        String duplicateEmails = searchEmailDuplicates(emails);
        StringBuilder duplicates = new StringBuilder();
        if (!duplicatePhones.isEmpty()) {
            duplicates.append("Such phones: " + duplicatePhones + " are already exist!");
        }
        if (!duplicateEmails.isEmpty()) {
            duplicates.append("Such emails: " + duplicateEmails + " are already exist!");
        }
        if (!duplicates.toString().isEmpty()) {
            throw new AlreadyExistsException(duplicates.toString());
        }
    }

    private String searchEmailDuplicates(List<EmailAddressDTO> emails) {
        return emails.stream()
                .filter(e -> contactRepository.findPeopleByEmail(e.getEmail()) != null)
                .map(EmailAddressDTO::getEmail)
                .collect(Collectors.joining(","));
    }

    private String searchPhoneDuplicates(List<PhoneNumberDTO> phones) {
        return phones.stream()
                .filter(p -> contactRepository.findByValue(p.getPhone()) != null)
                .map(PhoneNumberDTO::getPhone)
                .collect(Collectors.joining(","));
    }
}
