package pl.roman.mysan.contacts.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.roman.mysan.contacts.contact.domain.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
