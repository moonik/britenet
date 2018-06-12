package pl.roman.mysan.contacts.contact.model;

import lombok.Data;

@Data
public class EmailAddressDTO extends ContactDTO {
    private String email;

    public EmailAddressDTO(Long id, String email) {
        super(id);
        this.email = email;
    }
}
