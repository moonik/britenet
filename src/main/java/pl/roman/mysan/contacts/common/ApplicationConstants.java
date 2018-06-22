package pl.roman.mysan.contacts.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConstants {
    public static final String API_URL = "/api/contacts";

    public static final String INVALID_EMAIL_ADRESS = "Invalid format for email address: ";
    public static final String INVALID_PHONE = "Invalid format for phone number: ";
    public static final String INVALID_DATE_FORMAT = "Date can't be future or before 1918-01-01!\n";
    public static final String INVALID_NAME_OR_SURNAME = "Fields name and surname can't be empty!\n";
    public static final String INVALID_PESEL = "Pesel should contains 11 digits!\n";

    public static final String PHONE_PATTERN = "(^$|[0-9]{9})";
    public static final String EMAIL_PATTERN = ".+@.+\\..+";
    public static final String PESEL_PATTERN = "(^$|[0-9]{11})";
    public static final String VALID_PAST_DATE = "1918-01-01";
}
