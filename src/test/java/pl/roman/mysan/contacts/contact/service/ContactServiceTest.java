package pl.roman.mysan.contacts.contact.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.roman.mysan.contacts.TestDataFactory;
import pl.roman.mysan.contacts.common.DuplicateValidator;
import pl.roman.mysan.contacts.contact.domain.EmailAddress;
import pl.roman.mysan.contacts.contact.domain.PhoneNumber;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PersonContactDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
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

    @Before
    public void setup() {
        initMocks(this);
        contactService = new ContactService(contactRepository, personRepository, duplicateValidator);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileAddingNewContacts() {
        //given
        Long id = 1L;
        PersonContactDTO personContactDTO = TestDataFactory.personContactDtoWithInvalidData();
        String expectedMessage = "Invalid format for email address: romangmail.com,roman@.com,roman@com,roman@gmail,roman\n" +
                "Invalid format for phone number: 1,32193879387319783,321987dkjalskdja,312dasd213";

        try {
            //when
            contactService.addContacts(id, personContactDTO);
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
        Long id = 1L;
        PersonContactDTO personContactDTO = TestDataFactory.personContactDtoWithValidData();

        //and
        when(personRepository.existsById(id)).thenReturn(NOT_EXIST);

        //when
        contactService.addContacts(id, personContactDTO);

        //then throw NotFoundException
    }

    @Test
    public void shouldAddNewContacts() {
        //given
        Long id = 1L;
        PersonContactDTO personContactDTO = TestDataFactory.personContactDtoWithValidData();
        Person person = TestDataFactory.personWithContacts();

        //and
        when(personRepository.existsById(id)).thenReturn(EXIST);
        when(personRepository.getOne(id)).thenReturn(person);

        //when
        contactService.addContacts(id, personContactDTO);

        //then
        verify(personRepository, times(1)).getOne(id);
        verify(personRepository, times(1)).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileEditing() {
        //given
        EmailAddressDTO emailAddressDTO = new EmailAddressDTO(1L, "roman@gmail.com");

        //and
        when(personRepository.existsById(emailAddressDTO.getId())).thenReturn(NOT_EXIST);

        //when
        contactService.edit(emailAddressDTO);

        //then throw NotFoundException
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileEditingPhone() {
        //given
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO(1L, "1234567");
        PhoneNumber phoneNumber = new PhoneNumber(TestDataFactory.personWithContacts(), "123456789");
        String expectedMessage = "Invalid format for phone number: 1234567";

        //and
        when(contactRepository.existsById(phoneNumberDTO.getId())).thenReturn(EXIST);
        when(contactRepository.getOne(phoneNumberDTO.getId())).thenReturn(phoneNumber);

        //when
        try {
            contactService.edit(phoneNumberDTO);
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
        EmailAddressDTO emailAddressDTO = new EmailAddressDTO(1L, "romangmail.com");
        EmailAddress emailAddress = new EmailAddress(TestDataFactory.personWithContacts(), "rweq@gmail.com");
        String expectedMessage = "Invalid format for email address: romangmail.com";

        //and
        when(contactRepository.existsById(emailAddressDTO.getId())).thenReturn(EXIST);
        when(contactRepository.getOne(emailAddressDTO.getId())).thenReturn(emailAddress);

        //when
        try {
            contactService.edit(emailAddressDTO);
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
        PhoneNumberDTO phoneNumberDTO = new PhoneNumberDTO(1L, "111111111");
        PhoneNumber phoneNumber = new PhoneNumber(TestDataFactory.personWithContacts(), "123456789");

        //and
        when(contactRepository.existsById(phoneNumberDTO.getId())).thenReturn(EXIST);
        when(contactRepository.getOne(phoneNumberDTO.getId())).thenReturn(phoneNumber);

        //when
        contactService.edit(phoneNumberDTO);

        //then
        verify(contactRepository, times(1)).saveAndFlush(any(PhoneNumber.class));
    }

    @Test
    public void shouldEditEmailAddress() {
        //given
        EmailAddressDTO emailAddressDTO = new EmailAddressDTO(1L, "roman.mysan@gmail.com");
        EmailAddress emailAddress = new EmailAddress(TestDataFactory.personWithContacts(), "rweq@gmail.com");

        //and
        when(contactRepository.existsById(emailAddressDTO.getId())).thenReturn(EXIST);
        when(contactRepository.getOne(emailAddressDTO.getId())).thenReturn(emailAddress);

        //when
        contactService.edit(emailAddressDTO);

        //then
        verify(contactRepository, times(1)).saveAndFlush(any(EmailAddress.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileDeletingContact() {
        //given
        Long id = 1L;

        //and
        when(contactRepository.existsById(id)).thenReturn(NOT_EXIST);

        //when
        contactService.delete(id);

        //then
        verify(contactRepository, times(1)).deleteById(id);
    }

    @Test
    public void shouldDeleteContact() {
        //given
        Long id = 1L;

        //and
        when(contactRepository.existsById(id)).thenReturn(EXIST);

        //when
        contactService.delete(id);

        //then
        verify(contactRepository, times(1)).deleteById(id);
    }
}
