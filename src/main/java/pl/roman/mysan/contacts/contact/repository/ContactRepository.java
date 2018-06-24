package pl.roman.mysan.contacts.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    EmailAddress findByEmail(String email);
    PhoneNumber findByPhone(String phone);
}