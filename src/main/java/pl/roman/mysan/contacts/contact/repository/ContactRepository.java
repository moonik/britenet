package pl.roman.mysan.contacts.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.person.domain.Person;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    PhoneNumber findByValue(String phone);
    @Query("SELECT contact.person FROM EmailAddress contact WHERE contact.value = :email")
    Person findPeopleByEmail(@Param("email") String email);
    @Query("SELECT contact.person FROM EmailAddress contact WHERE contact.value like %:pattern%")
    List<Person> findPeopleByPattern(@Param("pattern") String pattern);
}