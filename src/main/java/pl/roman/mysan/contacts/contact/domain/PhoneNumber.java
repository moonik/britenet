package pl.roman.mysan.contacts.contact.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Pattern;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("phone")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PhoneNumber extends Contact {

    @Pattern(regexp="(^$|[0-9]{9})")
    @Column(unique = true)
    private String value;

    @Override
    public void edit(ContactDTO contactDTO) {
        this.value = ((PhoneNumberDTO) contactDTO).getValue();
    }
}
