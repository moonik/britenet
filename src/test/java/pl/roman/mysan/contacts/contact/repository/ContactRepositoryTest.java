package pl.roman.mysan.contacts.contact.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.roman.mysan.contacts.Application;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.person.domain.Person;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static pl.roman.mysan.contacts.TestHelper.assertThatListContainsExactly;
import static pl.roman.mysan.contacts.TestHelper.extractPesels;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void shouldReturnPhoneWhileSearchingByPhone() {
        //given
        String phone = "123456789";

        //when
        PhoneNumber phoneNumber = contactRepository.findByValue(phone);

        //then
        assertNotNull(phoneNumber);
        assertEquals(phone, phoneNumber.getValue());
    }

    @Test
    public void shouldReturnPersonWhileSearchingByEmail() {
        //given
        String email = "smith777@gmail.com";
        String expectedPesel = "12345678901";

        //when
        Person person = contactRepository.findPeopleByEmail(email);

        //then
        assertNotNull(person);
        assertEquals(expectedPesel, person.getPesel());
    }

    @Test
    public void shouldReturnEmptyListWhileSearchingByEmail() {
        //given
        String email = "test@gmail.com";

        //when
        Person person = contactRepository.findPeopleByEmail(email);

        //then
        assertNull(person);
    }

    @Test
    public void shouldReturnPeopleWhileSearchingByEmailPattern() {
        //given
        String pattern = "@gmail";
        String[] expectedPersonsPesels = {"12345678901", "12345678903"};

        //when
        List<Person> people = contactRepository.findPeopleByPattern(pattern);

        assertTrue(assertThatListContainsExactly(extractPesels(people), expectedPersonsPesels));
    }

    @Test
    public void shouldReturnEmptyListWhileSearchingByEmailPattern() {
        //given
        String pattern = "@yandex";
        int expectedListSize = 0;

        //when
        List<Person> people = contactRepository.findPeopleByPattern(pattern);

        //then
        assertEquals(expectedListSize, people.size());
    }
}
