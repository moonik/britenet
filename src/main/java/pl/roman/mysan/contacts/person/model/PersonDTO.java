package pl.roman.mysan.contacts.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class PersonDTO {

    private Long id;
    private String name;
    private String surname;
    private Character gender;
    private LocalDate birthDate;
    private String pesel;
    private PersonContactDTO contacts;
}
