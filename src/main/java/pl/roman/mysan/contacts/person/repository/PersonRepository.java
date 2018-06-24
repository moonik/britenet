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
    @Query("SELECT person FROM Person person JOIN FETCH person.contacts contact WHERE contact.email = :email")
    List<Person> findPeopleByEmail(@Param("email") String email);
    @Query("SELECT person FROM Person person JOIN FETCH person.contacts contact WHERE contact.email like %:pattern%")
    List<Person> findPeopleByPattern(@Param("pattern") String pattern);
}
