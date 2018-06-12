package pl.roman.mysan.contacts.contact.model;

import lombok.Data;

@Data
public class PhoneNumberDTO extends ContactDTO {
    private String phoneNumber;

    public PhoneNumberDTO(Long id, String phoneNumber) {
        super(id);
        this.phoneNumber = phoneNumber;
    }
}
