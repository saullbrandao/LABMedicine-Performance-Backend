package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ValidationErrorResponseDTO {
    private final String field;
    private final String message;

    public ValidationErrorResponseDTO(FieldError error) {
        this.field = error.getField();
        this.message = error.getDefaultMessage();
    }
}
