package pl.roman.mysan.contacts.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.roman.mysan.contacts.person.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
