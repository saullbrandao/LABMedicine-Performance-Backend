package devinphilips.squad5.backend.labmedicine.dtos.validators;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DateValidator.class })
@Documented
public @interface Date {
    String message() default "Use o padrão yyyy-MM-dd";

    Class<?>[] groups() default {};

    Class<? extends jakarta.validation.Payload>[] payload() default {};
}
