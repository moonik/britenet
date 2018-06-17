package pl.roman.mysan.contacts.contact.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.person.domain.Person;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("email")
@NoArgsConstructor
@Getter @Setter
public class EmailAddress extends Contact {

    @Email
    private String value;

    public EmailAddress(Person person, String value) {
        super(person);
        this.value = value;
    }

    @Override
    public void edit(ContactDTO contactDTO) {
        this.value = ((EmailAddressDTO) contactDTO).getValue();
    }
}
