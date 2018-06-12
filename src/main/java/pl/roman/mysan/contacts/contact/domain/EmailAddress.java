package pl.roman.mysan.contacts.contact.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("email")
@AllArgsConstructor
@Data
public class EmailAddress extends Contact {

    @Email
    private String email;

    @Override
    public void edit(ContactDTO contactDTO) {
        this.email = ((EmailAddressDTO) contactDTO).getEmail();
    }
}
