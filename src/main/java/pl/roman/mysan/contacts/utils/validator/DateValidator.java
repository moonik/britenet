package pl.roman.mysan.contacts.utils.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<DateConstraint, LocalDate> {

    @Override
    public void initialize(DateConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate past = LocalDate.parse("1918-01-01");
        LocalDate future = LocalDate.now().plusDays(1);
        return birthDate.isAfter(past) && birthDate.isBefore(future);
    }
}
