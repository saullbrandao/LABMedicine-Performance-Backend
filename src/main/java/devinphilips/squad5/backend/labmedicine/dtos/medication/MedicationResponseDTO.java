package devinphilips.squad5.backend.labmedicine.dtos.medication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import devinphilips.squad5.backend.labmedicine.enums.MedicationType;
import devinphilips.squad5.backend.labmedicine.enums.MedicationUnit;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.utils.LocalDateDeserializer;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MedicationResponseDTO(
         String name,

         String date,

         String time,

         String type,

         BigDecimal quantity,

         String unit,

         String observations,

         Integer patientId
) {
}
