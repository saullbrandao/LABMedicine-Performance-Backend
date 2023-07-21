package devinphilips.squad5.backend.labmedicine.exceptions;

import devinphilips.squad5.backend.labmedicine.dtos.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRequest(MethodArgumentNotValidException ex) {
        var field = Objects.requireNonNull(ex.getFieldError()).getField();
        var value = Objects.requireNonNull(ex.getFieldError()).getRejectedValue();
        var exceptionResponse = new ExceptionResponse(
                String.format(value == null ? "Required property [%s]" : "Invalid property [%s]", field),
                400);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        var pattern = Pattern.compile("(?<=.enums\\.)(.*)(?=\\.)");
        var matcher = pattern.matcher(ex.getMessage());
        var message = matcher.find()
                ? String.format("Invalid property [%s]", matcher.group(1).toLowerCase())
                : "";

        var exceptionResponse = new ExceptionResponse(message, 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body((exceptionResponse));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var message = switch (((ConstraintViolationException) ex.getCause()).getConstraintName()) {
            case "LABMEDICINE.CPF_UNIQUE" -> "Conflict [cpf]";
            case "LABMEDICINE.EMAIL_UNIQUE" -> "Conflict [email]";
            default -> "";
        };

        var exceptionResponse = new ExceptionResponse(message, 409);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponse);
    }
}
