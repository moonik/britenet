package pl.roman.mysan.contacts.person.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import pl.roman.mysan.contacts.TestDataFactory;
import pl.roman.mysan.contacts.common.DuplicateValidator;
import pl.roman.mysan.contacts.contact.repository.ContactRepository;
import pl.roman.mysan.contacts.exceptions.AlreadyExistsException;
import pl.roman.mysan.contacts.exceptions.NotFoundException;
import pl.roman.mysan.contacts.exceptions.ValidationException;
import pl.roman.mysan.contacts.person.domain.Person;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private ContactRepository contactRepository;
    @Mock
    private DuplicateValidator duplicateValidator;

    private PersonService personService;

    private static final Boolean EXIST = true;
    private static final Boolean NOT_EXIST = false;

    @Before
    public void setup() {
        initMocks(this);
        personService = new PersonService(personRepository, duplicateValidator);
    }

    @Test(expected = AlreadyExistsException.class)
    public void shouldThrowAlreadyExistsExceptionWhileSavingNewPerson() {
        //given
        String pesel = "12345678900";
        PersonDTO personDTO = TestDataFactory.personDtoWithDuplicatedPesel();

        //and
        when(personRepository.findByPesel(pesel)).thenReturn(TestDataFactory.personWithContacts());

        //when
        personService.save(personDTO);

        //then throw AlreadyExistsException
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileSavingNewPerson() {
        //given
        PersonDTO personDTO = TestDataFactory.personDtoWithInvalidData();
        String expectedMessage = "Date can't be future or before 1918-01-01!\n" +
                "Fields name and surname can't be empty!\n" +
                "Pesel should contains 11 digits!\n" +
                "Invalid format for email address: romangmail.com,roman@.com,roman@com,roman@gmail,roman\n" +
                "Invalid format for phone number: 1,32193879387319783,321987dkjalskdja,312dasd213";
        try {
            //when
            personService.save(personDTO);
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenBirthDateIsFuture() {
        //given
        String expectedMessage = "Date can't be future or before 1918-01-01!";
        PersonDTO personDTO = TestDataFactory.personDtoWithFutureDate();

        try {
            //when
            personService.save(personDTO);
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenBirthDateIsInvalidPast() {
        //given
        String expectedMessage = "Date can't be future or before 1918-01-01!";
        PersonDTO personDTO = TestDataFactory.personDtoWithInvalidPastDate();

        try {
            //when
            personService.save(personDTO);
        }
        catch(RuntimeException ex) {
            //then
            assertEquals(expectedMessage, ex.getMessage());
            throw ex;
        }
    }

    @Test
    public void shouldSaveNewPerson() {
        //given
        PersonDTO personDTO = TestDataFactory.personDtoWithValidData();
        Person expectedPerson = TestDataFactory.personWithContacts();

        //and
        when(personRepository.save(any(Person.class))).thenReturn(expectedPerson);

        //when
        personService.save(personDTO);

        //then
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhileEditingPerson() {
        //given
        PersonDTO personDTO = TestDataFactory.personDtoWithInvalidData();

        //when
        personService.edit(personDTO);

        //then throw ValidationException
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhenEditingPerson() {
        //given
        PersonDTO personDTO = TestDataFactory.personDtoWithValidData();

        //and
        when(personRepository.existsById(personDTO.getId())).thenReturn(NOT_EXIST);

        //when
        personService.edit(personDTO);

        //then throw NotFoundException
    }

    @Test
    public void shouldEditPerson() {
        //given
        String name = "Batman";
        PersonDTO personDTO = TestDataFactory.personDtoWithValidData();
        Person oldPerson = TestDataFactory.personWithContacts();

        //and
        personDTO.setName(name);
        when(personRepository.existsById(personDTO.getId())).thenReturn(EXIST);
        when(personRepository.getOne(personDTO.getId())).thenReturn(oldPerson);

        //when
        personService.edit(personDTO);

        //then
        verify(personRepository, times(1)).saveAndFlush(any(Person.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundExceptionWhileDeletingPerson() {
        //given
        Long id = 1L;

        //and
        when(personRepository.existsById(id)).thenReturn(NOT_EXIST);

        //when
        personService.delete(id);

        //then throw NotFoundException
    }

    @Test
    public void shouldDeletePerson() {
        //given
        Long id = 1L;

        //and
        when(personRepository.existsById(id)).thenReturn(EXIST);

        //when
        personService.delete(id);

        //then
        verify(personRepository, times(1)).deleteById(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhileSearchingPeople() {
        //given
        LocalDate firstDate = null;
        LocalDate secondDate = LocalDate.of(1995, 1, 1);

        //when
        personService.findPeopleByBirthDateBetween(firstDate, secondDate);

        //then throw exception
    }

    @Test
    public void shouldSearchPeopleByBirthDateBetween() {
        //given
        LocalDate firstDate = LocalDate.of(1995, 1, 1);
        LocalDate secondDate = LocalDate.of(2000, 1, 1);

        //and
        when(personRepository.findByBirthDateBetween(firstDate, secondDate)).thenReturn(Collections.emptyList());

        //when
        personService.findPeopleByBirthDateBetween(firstDate, secondDate);

        //then
        verify(personRepository, times(1)).findByBirthDateBetween(firstDate, secondDate);
    }

    @Test
    public void shouldSearchPeopleByEmailPattern() {
        //given
        String email = "*gmail*";
        String emailPattern = "@gmail";

        //when
        personService.findPeopleByEmail(email);

        //then
        verify(personRepository, times(1)).findPeopleByPattern(emailPattern);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowInvalidArgumentExceptionWhileSearchingByEmail() {
        //given
        String email = "romangmail.com";

        //when
        personService.findPeopleByEmail(email);

        //then throw IllegalArgumentException
    }

    @Test
    public void shouldSearchPeopleByEmail() {
        //given
        String email = "roman@gmail.com";

        //and
        when(personService.findPeopleByEmail(email)).thenReturn(anyList());

        //when
        personService.findPeopleByEmail(email);

        //then
        verify(personRepository, times(1)).findPeopleByEmail(email);
    }
}
