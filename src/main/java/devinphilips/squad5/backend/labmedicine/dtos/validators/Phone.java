package devinphilips.squad5.backend.labmedicine.dtos.validators;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { PhoneValidator.class })
@Documented
public @interface Phone {
    String message() default "Envie apenas números ou use o padrão (99) 9 9999-9999";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
