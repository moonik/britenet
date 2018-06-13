package pl.roman.mysan.contacts.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.roman.mysan.contacts.contact.model.ContactDTO;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
public class PersonInfoDTO {
    private Long id;
    private String name;
    private String surname;
    private Character gender;
    private String birthDate;
    private String pesel;
    private List<ContactDTO> contacts;
}
