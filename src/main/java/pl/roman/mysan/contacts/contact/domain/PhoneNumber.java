package pl.roman.mysan.contacts.contact.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.domain.Person;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

import static pl.roman.mysan.contacts.common.ApplicationConstants.PHONE_PATTERN;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("phone")
@NoArgsConstructor
@Getter @Setter
public class PhoneNumber extends Contact {

    @Pattern(regexp= PHONE_PATTERN)
    @Column(unique = true)
    private String value;

    public PhoneNumber(Person person, String value) {
        super(person);
        this.value = value;
    }

    @Override
    public void edit(ContactDTO contactDTO) {
        this.value = ((PhoneNumberDTO) contactDTO).getPhone();
    }
}
