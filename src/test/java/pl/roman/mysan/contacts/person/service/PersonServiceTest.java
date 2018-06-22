package pl.roman.mysan.contacts.person.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.roman.mysan.contacts.person.model.PersonDTO;
import pl.roman.mysan.contacts.person.repository.PersonRepository;

import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    private PersonService personService;

    @Before
    public void setup() {
        initMocks(this);
        personService = new PersonService(personRepository);
    }

    @Test
    public void shouldSavePerson() {
        //given

    }
}
