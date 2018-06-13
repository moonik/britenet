package pl.roman.mysan.contacts.contact.asm;

import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;

public class ContactAsm {

    public static Contact createEntityObject(ContactDTO contactDTO) {
        if (contactDTO instanceof PhoneNumberDTO) {
            return new PhoneNumber(((PhoneNumberDTO) contactDTO).getValue());
        } else
            return new EmailAddress(((EmailAddressDTO) contactDTO).getValue());
    }

    public static ContactDTO createDtoObject(Contact contact) {
        if (contact instanceof PhoneNumber) {
            return new PhoneNumberDTO(contact.getId(), ((PhoneNumber) contact).getValue());
        } else
            return new EmailAddressDTO(contact.getId(), ((EmailAddress) contact).getValue());
    }
}
