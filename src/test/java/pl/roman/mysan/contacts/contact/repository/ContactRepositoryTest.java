package pl.roman.mysan.contacts.contact.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.roman.mysan.contacts.Application;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

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
        PhoneNumber phoneNumber = contactRepository.findByPhone(phone);

        //then
        assertNotNull(phoneNumber);
        assertEquals(phone, phoneNumber.getPhone());
    }

    @Test
    public void shouldReturnEmailWhileSearchingByPhone() {
        //given
        String email = "swift@yahoo.com";

        //when
        EmailAddress emailAddress = contactRepository.findByEmail(email);

        //then
        assertNotNull(emailAddress);
        assertEquals(email, emailAddress.getEmail());
    }
}
