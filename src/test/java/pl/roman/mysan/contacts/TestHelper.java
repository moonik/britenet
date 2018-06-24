package pl.roman.mysan.contacts;

import pl.roman.mysan.contacts.person.domain.Person;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestHelper {

    public static List<String> extractPesels(List<Person> people) {
        return people.stream()
                .map(Person::getPesel)
                .collect(Collectors.toList());
    }

    public static boolean assertThatListContainsExactly(List<String> values, String... expectedValues) {
        List<String> collectedValues = Arrays.stream(expectedValues).filter(values::contains)
                .collect(Collectors.toList());
        return collectedValues.size() == expectedValues.length;
    }
}
