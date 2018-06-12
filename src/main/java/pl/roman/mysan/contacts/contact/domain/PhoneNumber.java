package pl.roman.mysan.contacts.contact.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("phoneNumber")
@AllArgsConstructor
@Data
public class PhoneNumber extends Contact {

    @Pattern(regexp="(^$|[0-9]{9})")
    private String phoneNumber;

    @Override
    public void edit(ContactDTO contactDTO) {
        this.phoneNumber = ((PhoneNumberDTO) contactDTO).getPhoneNumber();
    }
}
