package pl.roman.mysan.contacts.common;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.roman.mysan.contacts.TestDataFactory;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;
import pl.roman.mysan.contacts.person.domain.Person;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class DuplicateValidatorTest {
    @Mock
    private ContactRepository contactRepository;

    private DuplicateValidator duplicateValidator;

    @Before
    public void setup() {
        initMocks(this);
        duplicateValidator = new DuplicateValidator(contactRepository);
    }

    @Test(expected = AlreadyExistsException.class)
    public void shouldThrowAlreadyExistException() {
        //given
        PersonContactDTO contacts = TestDataFactory.personContactDtoWithValidData();
        PhoneNumber anyPhoneNumber = new PhoneNumber();
        EmailAddress anyEmail = new EmailAddress();
        String phone = "123456789";
        String email = "roman@gmail.com";
        String expectedMessage = "Such phones: 123456789 are already exist!" +
                "Such emails: roman@gmail.com are already exist!";

        //and
        when(contactRepository.findByPhone(phone)).thenReturn(anyPhoneNumber);
        when(contactRepository.findByEmail(email)).thenReturn(anyEmail);

        try {
            //when
            duplicateValidator.validateDuplicates(contacts.getPhones(), contacts.getEmails());
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void shouldPassValidation() {
        //given
        PersonContactDTO contacts = TestDataFactory.personContactDtoWithValidData();

        //when
        duplicateValidator.validateDuplicates(contacts.getPhones(), contacts.getEmails());

        //then no exception expected
    }
}
