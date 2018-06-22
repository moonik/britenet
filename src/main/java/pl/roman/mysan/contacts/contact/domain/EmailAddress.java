package pl.roman.mysan.contacts.contact.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.person.domain.Person;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

import static pl.roman.mysan.contacts.common.ApplicationConstants.EMAIL_PATTERN;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("email")
@NoArgsConstructor
@Getter @Setter
public class EmailAddress extends Contact {

    @Pattern(regexp= EMAIL_PATTERN)
    private String value;

    public EmailAddress(Person person, String value) {
        super(person);
        this.value = value;
    }

    @Override
    public void edit(ContactDTO contactDTO) {
        this.value = ((EmailAddressDTO) contactDTO).getEmail();
    }
}
