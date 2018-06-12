package pl.roman.mysan.contacts.person.domain;

import lombok.Builder;
import lombok.Data;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.person.model.PersonDTO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
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

    //TODO add annotation to validate date
    private LocalDate birthDate;

    @Size(min = 11, max = 11)
    private Integer pesel;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Contact> contacts;

    public void edit(PersonDTO personDTO) {
        this.name = personDTO.getName();
        this.surname = personDTO.getSurname();
        this.gender = personDTO.getGender();
        this.birthDate = LocalDate.parse(personDTO.getBirthDate());
        this.pesel = personDTO.getPesel();
    }
}
