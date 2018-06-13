package pl.roman.mysan.contacts.person.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.service.PersonService;

import javax.validation.Valid;

import static pl.roman.mysan.contacts.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/person")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/")
    public void save(@Valid @RequestBody PersonDTO personDTO) {
        personService.save(personDTO);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO) {
        personService.edit(id, personDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }
}
