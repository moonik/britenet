package pl.roman.mysan.contacts.contact.asm;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.domain.Person;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactAsm {

    public static Contact createEntityObject(ContactDTO contactDTO, Person person) {
        if (contactDTO instanceof PhoneNumberDTO) {
            return new PhoneNumber(person, ((PhoneNumberDTO) contactDTO).getPhone());
        } else
            return new EmailAddress(person, ((EmailAddressDTO) contactDTO).getEmail());
    }

    public static ContactDTO createDtoObject(Contact contact) {
        if (contact instanceof PhoneNumber) {
            return new PhoneNumberDTO(contact.getId(), ((PhoneNumber) contact).getValue());
        } else
            return new EmailAddressDTO(contact.getId(), ((EmailAddress) contact).getValue());
    }

    public static List<ContactDTO> collectContacts(List<EmailAddressDTO> emails, List<PhoneNumberDTO> phones) {
        List<ContactDTO> contacts = new ArrayList<>();
        contacts.addAll(emails);
        contacts.addAll(phones);
        return contacts;
    }
}
