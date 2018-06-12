package pl.roman.mysan.contacts.person.model;

import lombok.Builder;
import lombok.Data;
import pl.roman.mysan.contacts.contact.model.ContactDTO;

import java.util.List;

@Data
@Builder
public class PersonDTO {

    private Long id;
    private String name;
    private String surname;
    private Character gender;
    private String birthDate;
    private Integer pesel;
    private List<ContactDTO> contacts;
}
