package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.ExceptionResponse;
import devinphilips.squad5.backend.labmedicine.dtos.ValidationErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.regex.Pattern;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponseDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(
                errors.stream().map(ValidationErrorResponseDTO::new).collect(Collectors.toList()));
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<List<ValidationErrorResponseDTO>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        var field = switch (((ConstraintViolationException) ex.getCause()).getConstraintName()) {
            case "LABMEDICINE.CPF_UNIQUE", "LABMEDICINE.USER_CPF_UNIQUE" -> "cpf";
            case "LABMEDICINE.EMAIL_UNIQUE", "LABMEDICINE.USER_EMAIL_UNIQUE" -> "email";
            default -> "";
        };
        var message = "Conflito com valores já cadastrados.";

        return ResponseEntity.status(HttpStatus.CONFLICT).body(List.of(new ValidationErrorResponseDTO(field, message)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<List<ValidationErrorResponseDTO>> handleIllegalArgumentException(IllegalArgumentException ex) {
        var pattern = Pattern.compile("(?<=.enums\\.)(.*)(?=\\.)");
        var matcher = pattern.matcher(ex.getMessage());
        var field = matcher.find() ? matcher.group(1).toLowerCase() : "";
        var message = "Valor inválido.";

        return ResponseEntity.badRequest().body(List.of(new ValidationErrorResponseDTO(field, message)));
    }
}
