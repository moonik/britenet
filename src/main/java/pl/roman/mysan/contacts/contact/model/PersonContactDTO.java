package pl.roman.mysan.contacts.contact.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class PersonContactDTO {
    private List<EmailAddressDTO> emails;
    private List<PhoneNumberDTO> phones;
}
