package pl.roman.mysan.contacts.contact.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
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
        if (!personContactDTO.getEmails().isEmpty()) {
            contactService.addEmails(id, personContactDTO.getEmails());
        }
        if (!personContactDTO.getPhones().isEmpty()) {
            contactService.addPhones(id, personContactDTO.getPhones());
        }
    }

    @PutMapping("/")
    public void edit(@Valid @RequestBody ContactDTO contactDTO) {
        contactService.edit(contactDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
}
