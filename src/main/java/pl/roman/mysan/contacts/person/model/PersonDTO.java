package pl.roman.mysan.contacts.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class PersonDTO {

    private String name;
    private String surname;
    private Character gender;
    private String birthDate;
    private String pesel;
    private PersonContactDTO contacts;
}
