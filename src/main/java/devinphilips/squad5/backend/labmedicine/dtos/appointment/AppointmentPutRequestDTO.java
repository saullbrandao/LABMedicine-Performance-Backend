package devinphilips.squad5.backend.labmedicine.dtos.appointment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import devinphilips.squad5.backend.labmedicine.utils.LocalDateDeserializer;
import devinphilips.squad5.backend.labmedicine.utils.LocalTimeDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentPutRequestDTO (
        @NotBlank(message = "Campo obrigatório.")
        @Size(min = 8, max = 64, message = "Mínimo 8 e máximo 64 caracteres.")
        String reason,
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @NotNull(message = "Campo obrigatório. Use o padrão yyyy-MM-dd.")
        LocalDate date,
        @JsonDeserialize(using = LocalTimeDeserializer.class)
        @NotNull(message = "Campo obrigatório. Use o padrão HH:mm.")
        LocalTime time,
        @NotBlank(message = "Campo obrigatório.")
        @Size(min = 16, max = 1024, message = "Mínimo 16 e máximo 1024 caracteres.")
        String description,
        String medication,
        @NotBlank(message = "Campo obrigatório.")
        @Size(min = 16, max = 256, message = "Mínimo 16 e máximo 256 caracteres.")
        String dosageAndPrecautions,
        @NotNull(message = "Campo obrigatório.")
        Boolean status
){

}
