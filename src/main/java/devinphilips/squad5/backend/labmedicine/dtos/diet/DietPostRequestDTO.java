package devinphilips.squad5.backend.labmedicine.dtos.diet;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import devinphilips.squad5.backend.labmedicine.enums.DietType;
import devinphilips.squad5.backend.labmedicine.enums.ExerciseType;
import devinphilips.squad5.backend.labmedicine.utils.LocalDateDeserializer;
import devinphilips.squad5.backend.labmedicine.utils.LocalTimeDeserializer;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;

public record DietPostRequestDTO(
        @NotBlank(message = "Campo obrigatório.")
        @Size(min = 5, max = 100, message = "Mínimo 5 e máximo 100 caracteres.")
        String name,

        @JsonDeserialize(using = LocalDateDeserializer.class)
        @NotNull(message = "Campo obrigatório. Use o padrão yyyy-MM-dd.")
        LocalDate date,

        @JsonDeserialize(using = LocalTimeDeserializer.class)
        @NotNull(message = "Campo obrigatório. Use o padrão HH:mm.")
        LocalTime time,

        @NotNull(message = "Campo obrigatório.")
        DietType type,
        
        @NotBlank(message = "Campo obrigatório.")
        @Size(min = 10, max = 1000, message = "Mínimo 10 e máximo 1000 caracteres.")
        String description,

        @NotNull(message = "Campo obrigatório.")
        Integer patientId
) {
}
