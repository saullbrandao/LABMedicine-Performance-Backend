package devinphilips.squad5.backend.labmedicine.dtos.validators;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CEPValidator.class })
@Documented
public @interface CEP {
    String message() default "Formato inválido.";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
