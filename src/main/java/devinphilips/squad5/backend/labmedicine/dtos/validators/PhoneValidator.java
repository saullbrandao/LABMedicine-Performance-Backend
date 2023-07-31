package devinphilips.squad5.backend.labmedicine.dtos.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;

        return value.matches("^[0-9]{11}$") || value.matches("^(\\([0-9]{2}\\) 9 [0-9]{4}-[0-9]{4})$");
    }
}
