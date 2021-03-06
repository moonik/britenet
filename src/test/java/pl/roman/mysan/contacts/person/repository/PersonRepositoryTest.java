package pl.roman.mysan.contacts.person.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.roman.mysan.contacts.Application;
import pl.roman.mysan.contacts.person.domain.Person;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static pl.roman.mysan.contacts.TestHelper.assertThatListContainsExactly;
import static pl.roman.mysan.contacts.TestHelper.extractPesels;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void shouldSearchPersonByPesel() {
        //given
        String pesel = "12345678901";

        //when
        Person person = personRepository.findByPesel(pesel);

        //then
        assertNotNull(person);
        assertEquals(pesel, person.getPesel());
    }

    @Test
    public void shouldReturnPeopleWhileSearchingByBirthDateBetween() {
        //given
        LocalDate first = LocalDate.of(1989, 1, 1);
        LocalDate second = LocalDate.of(2000, 1, 1);
        String[] expectedPersonsPesels = {"12345678901", "12345678903"};

        //when
        List<Person> people = personRepository.findByBirthDateBetween(first, second);

        //then
        assertNotNull(people);
        assertTrue(assertThatListContainsExactly(extractPesels(people), expectedPersonsPesels));
    }

    @Test
    public void shouldReturnEmptyListWhileSearchingByBirthDateBetween() {
        //given
        LocalDate first = LocalDate.of(2001, 1, 1);
        LocalDate second = LocalDate.of(2009, 1, 1);
        int expectedListSize = 0;

        //when
        List<Person> people = personRepository.findByBirthDateBetween(first, second);

        //then
        assertEquals(expectedListSize, people.size());
    }

    @Test
    public void shouldReturnPersonWhileSearchingByEmail() {
        //given
        String email = "smith777@gmail.com";
        String expectedPesel = "12345678901";

        //when
        List<Person> person = personRepository.findPeopleByEmail(email);

        //then
        assertNotNull(person);
        assertEquals(expectedPesel, person.get(0).getPesel());
    }

    @Test
    public void shouldReturnEmptyListWhileSearchingByEmail() {
        //given
        String email = "test@gmail.com";
        int expectedListSize = 0;

        //when
        List<Person> person = personRepository.findPeopleByEmail(email);

        //then
        assertEquals(expectedListSize, person.size());
    }

    @Test
    public void shouldReturnPeopleWhileSearchingByEmailPattern() {
        //given
        String pattern = "@gmail";
        String[] expectedPersonsPesels = {"12345678901", "12345678903"};

        //when
        List<Person> people = personRepository.findPeopleByPattern(pattern);

        assertTrue(assertThatListContainsExactly(extractPesels(people), expectedPersonsPesels));
    }

    @Test
    public void shouldReturnEmptyListWhileSearchingByEmailPattern() {
        //given
        String pattern = "@yandex";
        int expectedListSize = 0;

        //when
        List<Person> people = personRepository.findPeopleByPattern(pattern);

        //then
        assertEquals(expectedListSize, people.size());
    }
}
