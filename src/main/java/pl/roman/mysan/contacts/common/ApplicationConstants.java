package pl.roman.mysan.contacts.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationConstants {
    public static final String API_URL = "/api/contacts";
    public static final String INVALID_FORMAT_FOR_EMAIL_ADRESS = "Invalid format for email address: ";
    public static final String INVALID_FORMAT_FOR_PHONE = "Invalid format for phone number: ";
    public static final String PHONE_PATTERN = "(^$|[0-9]{9})";
    public static final String EMAIL_PATTERN = ".+@.+\\..+";
    public static final String PESEL_PATTERN = "(^$|[0-9]{11})";
    public static final String VALID_PAST_DATE = "1918-01-01";
}
