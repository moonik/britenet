package pl.roman.mysan.contacts.person.controller;

import contacts.mysan.roman.soap.GetSearchRequest;
import contacts.mysan.roman.soap.GetSearchResponse;
import lombok.AllArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.roman.mysan.contacts.person.service.PersonService;
import pl.roman.mysan.contacts.person.model.PersonInfoDTO;

import java.util.List;
import java.util.stream.Collectors;

@Endpoint
@AllArgsConstructor
public class PersonContactsEndpoint {

    private final PersonService personService;

    @PayloadRoot(namespace = "roman.mysan.contacts/soap",
            localPart = "getSearchRequest")
    @ResponsePayload
    public GetSearchResponse getSearchRequest(@RequestPayload GetSearchRequest request) {
        GetSearchResponse response = new GetSearchResponse();
        List<contacts.mysan.roman.soap.PersonInfoDTO> info = convertToXml(personService.findPeopleByEmail(request.getEmail()));
        response.setPersonInfoDTO(info);
        return response;
    }

    private static List<contacts.mysan.roman.soap.PersonInfoDTO> convertToXml(List<PersonInfoDTO> people) {
        return people.stream()
                .map(PersonContactsEndpoint::buildObject)
                .collect(Collectors.toList());
    }

    private static contacts.mysan.roman.soap.PersonInfoDTO buildObject(PersonInfoDTO p) {
        contacts.mysan.roman.soap.PersonInfoDTO person = new contacts.mysan.roman.soap.PersonInfoDTO();
        person.setBirthDate(p.getBirthDate());
        person.setGender(p.getGender() == 'M' ? 1 : 0);
        person.setId(p.getId());
        person.setName(p.getName());
        person.setPesel(p.getPesel());
        person.setSurname(p.getSurname());
        return person;
    }
}
