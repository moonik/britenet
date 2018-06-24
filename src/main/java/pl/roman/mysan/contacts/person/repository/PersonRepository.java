package pl.roman.mysan.contacts.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.roman.mysan.contacts.person.domain.Person;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByPesel(String pesel);
    List<Person> findByBirthDateBetween(LocalDate first, LocalDate second);
}
