package devinphilips.squad5.backend.labmedicine.dtos.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CEPValidator implements ConstraintValidator<CEP, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return true;

        return value.matches("^[0-9]{8}$") || value.matches("^[0-9]{5}-[0-9]{3}$");
    }
}
