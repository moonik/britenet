package pl.roman.mysan.contacts.person.controller;

import contacts.mysan.roman.soap.Contact;
import contacts.mysan.roman.soap.Email;
import contacts.mysan.roman.soap.GetSearchRequest;
import contacts.mysan.roman.soap.GetSearchResponse;
import contacts.mysan.roman.soap.PersonInfo;
import contacts.mysan.roman.soap.Phone;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.roman.mysan.contacts.common.ApplicationConstants;
import pl.roman.mysan.contacts.contact.model.ContactDTO;
import pl.roman.mysan.contacts.contact.model.EmailAddressDTO;
import pl.roman.mysan.contacts.contact.model.PhoneNumberDTO;
import pl.roman.mysan.contacts.person.service.PersonService;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
@AllArgsConstructor
public class PersonContactsEndpoint {

    private final PersonService personService;

    @PayloadRoot(namespace = ApplicationConstants.SOAP_URI,
            localPart = "getSearchRequest")
    @ResponsePayload
    public GetSearchResponse getSearchRequest(@RequestPayload GetSearchRequest request) {
        GetSearchResponse response = new GetSearchResponse();
        List<PersonInfo> people = convertToSoapResponse(personService.findPeopleByEmail(request.getEmail()));
        response.getPeople().addAll(people);
        return response;
    }

    private static List<PersonInfo> convertToSoapResponse(List<PersonInfoDTO> people) {
        return people.stream()
                .map(PersonContactsEndpoint::buildObject)
                .collect(Collectors.toList());
    }

    private static PersonInfo buildObject(PersonInfoDTO p) {
        PersonInfo person = new PersonInfo();
        person.setBirthDate(p.getBirthDate());
        person.setGender(p.getGender().toString());
        person.setId(p.getId());
        person.setName(p.getName());
        person.setPesel(p.getPesel());
        person.setSurname(p.getSurname());
        person.getContacts().addAll(convertContacts(p));
        return person;
    }

    private static List<Contact> convertContacts(PersonInfoDTO p) {
        return p.getContacts().stream()
                .map(PersonContactsEndpoint::convertContact)
                .collect(Collectors.toList());

    }

    private static Contact convertContact(ContactDTO contact) {
        if (contact instanceof PhoneNumberDTO) {
            Phone phone = new Phone();
            phone.setId(contact.getId());
            phone.setPhone(((PhoneNumberDTO) contact).getPhone());
            return phone;
        } else {
            Email email = new Email();
            email.setId(contact.getId());
            email.setEmail(((EmailAddressDTO) contact).getEmail());
            return email;
        }
    }
}
