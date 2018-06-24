package pl.roman.mysan.contacts.contact.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.roman.mysan.contacts.contact.asm.ContactAsm;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.service.ContactService;

import javax.validation.Valid;

import static pl.roman.mysan.contacts.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/contact")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/{id}")
    public void save(@PathVariable Long id, @RequestBody PersonContactDTO personContactDTO) {
        contactService.addContacts(id, personContactDTO);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable Long id, @Valid @RequestParam String value) {
        contactService.edit(id, value);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
}
