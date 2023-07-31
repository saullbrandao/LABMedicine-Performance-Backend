package devinphilips.squad5.backend.labmedicine.dtos.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<Date, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;

        try {
            LocalDate.parse(value);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
