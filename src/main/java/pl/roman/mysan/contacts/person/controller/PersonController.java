package pl.roman.mysan.contacts.person.controller;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;
import pl.roman.mysan.contacts.person.service.PersonService;

import javax.validation.Valid;

import java.time.LocalDate;
import java.util.List;

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

    @PutMapping("/")
    public void edit(@Valid @RequestBody PersonDTO personDTO) {
        personService.edit(personDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personService.delete(id);
    }

    @GetMapping("/search/between")
    public List<PersonInfoDTO> findPeopleByBirthDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate first,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate second) {
        return personService.findPeopleByBirthDateBetween(first, second);
    }

    @GetMapping("/search")
    public List<PersonInfoDTO> findPeopleByEmail(@RequestParam String email) {
        return personService.findPeopleByEmail(email);
    }
}
