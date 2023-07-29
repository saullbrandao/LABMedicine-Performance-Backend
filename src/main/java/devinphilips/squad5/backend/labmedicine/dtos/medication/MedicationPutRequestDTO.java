package devinphilips.squad5.backend.labmedicine.dtos.medication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import devinphilips.squad5.backend.labmedicine.enums.MedicationType;
import devinphilips.squad5.backend.labmedicine.enums.MedicationUnit;
import devinphilips.squad5.backend.labmedicine.utils.LocalDateDeserializer;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MedicationPutRequestDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 5, max = 100, message = "Mínimo 5 e máximo 100 caracteres.")
        String name,

        @JsonDeserialize(using = LocalDateDeserializer.class)
        @NotNull(message = "Campo obrigatório. Use o padrão yyyy-MM-dd.")
        LocalDateTime medicationDate,

        @NotNull(message = "Campo obrigatório.")
        MedicationType type,

        @NotNull(message = "Campo obrigatório.")
        @Digits(integer=5, fraction = 2,  message = "No minimo 2 casas decimais")
        BigDecimal quantity,


        @NotBlank(message = "Campo obrigatório")
        MedicationUnit unit,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 10, max = 1000, message = "Mínimo 10 e máximo 1000 caracteres.")
        String observations,

        @NotBlank(message = "Campo obrigatório")
        boolean status,

        @NotBlank(message = "Campo obrigatório")
        Integer patientId
) {
}
