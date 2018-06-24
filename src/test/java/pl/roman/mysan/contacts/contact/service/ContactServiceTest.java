package pl.roman.mysan.contacts.contact.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.roman.mysan.contacts.TestDataFactory;
import pl.roman.mysan.contacts.common.DuplicateValidator;
import pl.roman.mysan.contacts.contact.domain.Contact;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.NotFoundException;
import pl.roman.mysan.contacts.exceptions.ValidationException;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private DuplicateValidator duplicateValidator;

    private ContactService contactService;

    private static final Boolean EXIST = true;
    private static final Boolean NOT_EXIST = false;

    private static final Long ID = 1L;

    @Before
    public void setup() {
        initMocks(this);
        contactService = new ContactService(contactRepository, personRepository, duplicateValidator);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileAddingNewContacts() {
        //given
        PersonContactDTO personContactDTO = TestDataFactory.personContactDtoWithInvalidData();
        String expectedMessage = "Invalid format for email address: romangmail.com,roman@.com,roman@com,roman@gmail,roman\n" +
                "Invalid format for phone number: 1,32193879387319783,321987dkjalskdja,312dasd213";

        try {
            //when
            contactService.addContacts(ID, personContactDTO);
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileAddingNewContacts() {
        //given
        PersonContactDTO personContactDTO = TestDataFactory.personContactDtoWithValidData();

        //and
        when(personRepository.existsById(ID)).thenReturn(NOT_EXIST);

        //when
        contactService.addContacts(ID, personContactDTO);

        //then throw NotFoundException
    }

    @Test
    public void shouldAddNewContacts() {
        //given
        PersonContactDTO personContactDTO = TestDataFactory.personContactDtoWithValidData();
        Person person = TestDataFactory.personWithContacts();

        //and
        when(personRepository.existsById(ID)).thenReturn(EXIST);
        when(personRepository.getOne(ID)).thenReturn(person);

        //when
        contactService.addContacts(ID, personContactDTO);

        //then
        verify(personRepository, times(1)).getOne(ID);
        verify(personRepository, times(1)).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileEditing() {
        //given
        String email = "roman@gmail.com";

        //and
        when(personRepository.existsById(ID)).thenReturn(NOT_EXIST);

        //when
        contactService.edit(ID, email);

        //then throw NotFoundException
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileEditingPhone() {
        //given
        String number = "1234567";
        PhoneNumber phoneNumber = new PhoneNumber("123456789");
        String expectedMessage = "Invalid format for phone number: 1234567";

        //and
        when(contactRepository.existsById(ID)).thenReturn(EXIST);
        when(contactRepository.getOne(ID)).thenReturn(phoneNumber);

        //when
        try {
            contactService.edit(ID, number);
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileEditingEmail() {
        //given
        String email = "romangmail.com";
        EmailAddress emailAddress = new EmailAddress("rweq@gmail.com");
        String expectedMessage = "Invalid format for email address: romangmail.com";

        //and
        when(contactRepository.existsById(ID)).thenReturn(EXIST);
        when(contactRepository.getOne(ID)).thenReturn(emailAddress);

        //when
        try {
            contactService.edit(ID, email);
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void shouldEditPhoneNumber() {
        //given
        String number = "111111111";
        PhoneNumber phoneNumber = new PhoneNumber("123456789");

        //and
        when(contactRepository.existsById(ID)).thenReturn(EXIST);
        when(contactRepository.getOne(ID)).thenReturn(phoneNumber);

        //when
        contactService.edit(ID, number);

        //then
        verify(contactRepository, times(1)).saveAndFlush(any(PhoneNumber.class));
    }

    @Test
    public void shouldEditEmailAddress() {
        //given
        String email = "roman.mysan@gmail.com";
        EmailAddress emailAddress = new EmailAddress("rweq@gmail.com");

        //and
        when(contactRepository.existsById(ID)).thenReturn(EXIST);
        when(contactRepository.getOne(ID)).thenReturn(emailAddress);

        //when
        contactService.edit(ID, email);

        //then
        verify(contactRepository, times(1)).saveAndFlush(any(EmailAddress.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileDeletingContact() {
        //given

        //and
        when(contactRepository.existsById(ID)).thenReturn(NOT_EXIST);

        //when
        contactService.delete(ID, ID);

        //then
        verify(contactRepository, times(1)).deleteById(ID);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileDeletingContactWithInvalidPersonId() {
        //given

        //and
        when(contactRepository.existsById(ID)).thenReturn(EXIST);
        when(personRepository.existsById(ID)).thenReturn(NOT_EXIST);

        //when
        contactService.delete(ID, ID);

        //then
        verify(contactRepository, times(1)).deleteById(ID);
    }

    @Test
    public void shouldDeleteContact() {
        //given
        Person person = TestDataFactory.personWithContacts();
        Contact contact = person.getContacts().get(0);

        //and
        when(contactRepository.existsById(ID)).thenReturn(EXIST);
        when(personRepository.existsById(ID)).thenReturn(EXIST);
        when(personRepository.getOne(ID)).thenReturn(person);
        when(contactRepository.getOne(ID)).thenReturn(contact);

        //when
        contactService.delete(ID, ID);

        //then
        verify(personRepository, times(1)).saveAndFlush(any(Person.class));
        verify(contactRepository, times(1)).delete(contact);
    }
}
