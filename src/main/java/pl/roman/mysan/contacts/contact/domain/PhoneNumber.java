package pl.roman.mysan.contacts.contact.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String phone;

    public PhoneNumber(String phone) {
        this.phone = phone;
    }

    @Override
    public void edit(String value) {
        this.phone = value;
    }
}
