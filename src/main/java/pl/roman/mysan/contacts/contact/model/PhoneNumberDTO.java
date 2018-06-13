package pl.roman.mysan.contacts.contact.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class PhoneNumberDTO extends ContactDTO {
    private String value;

    public PhoneNumberDTO(Long id, String phoneNumber) {
        super(id);
        this.value = phoneNumber;
    }
}
