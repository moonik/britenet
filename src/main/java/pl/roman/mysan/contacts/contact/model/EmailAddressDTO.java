package pl.roman.mysan.contacts.contact.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class EmailAddressDTO extends ContactDTO {
    private String value;

    public EmailAddressDTO(Long id, String email) {
        super(id);
        this.value = email;
    }
}
