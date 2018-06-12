package pl.roman.mysan.contacts.contact.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.service.ContactService;

import java.util.List;

import static pl.roman.mysan.contacts.common.ApplicationConstants.API_URL;

@RestController
@RequestMapping(API_URL + "/contact")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/{id}/all")
    public List<ContactDTO> getAll(@PathVariable Long id) {
        return contactService.getAll(id);
    }

    @PostMapping("/{id}")
    public void save(@PathVariable Long id, @RequestBody ContactDTO contactDTO) {
        contactService.addNew(id, contactDTO);
    }

    @PutMapping("/")
    public void edit(@RequestBody ContactDTO contactDTO) {
        contactService.edit(contactDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }
}
