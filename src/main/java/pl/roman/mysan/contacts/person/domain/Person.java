package pl.roman.mysan.contacts.person.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.utils.validator.DateConstraint;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

import static pl.roman.mysan.contacts.common.ApplicationConstants.PESEL_PATTERN;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    private Character gender;

    @DateConstraint
    private LocalDate birthDate;

    @Pattern(regexp=PESEL_PATTERN)
    @Column(unique = true)
    private String pesel;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contact> contacts;

    public void edit(PersonDTO personDTO) {
        this.name = personDTO.getName();
        this.surname = personDTO.getSurname();
        this.gender = personDTO.getGender();
        this.birthDate = personDTO.getBirthDate();
        this.pesel = personDTO.getPesel();
    }
}
