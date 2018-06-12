package pl.roman.mysan.contacts.person.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.roman.mysan.contacts.person.asm.PersonAsm;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public void save(PersonDTO personDTO) {
        Person person = PersonAsm.createEntityObject(personDTO);
        personRepository.save(person);
    }

    public void edit(PersonDTO personDTO) {
        Person person = personRepository.getOne(personDTO.getId());
        person.edit(personDTO);
        personRepository.saveAndFlush(person);
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }
}
