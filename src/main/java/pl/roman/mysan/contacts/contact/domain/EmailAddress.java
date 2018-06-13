package pl.roman.mysan.contacts.contact.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("email")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EmailAddress extends Contact {

    @Email
    @Column(unique = true)
    private String value;

    @Override
    public void edit(ContactDTO contactDTO) {
        this.value = ((EmailAddressDTO) contactDTO).getValue();
    }
}
