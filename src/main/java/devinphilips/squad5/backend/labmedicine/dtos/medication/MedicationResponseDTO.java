package devinphilips.squad5.backend.labmedicine.dtos.medication;

import java.math.BigDecimal;

public record MedicationResponseDTO(
         Integer id,

         String name,

         String date,

         String time,

         String type,

         BigDecimal quantity,

         String unit,

         String observations,

         Integer patientId,

         String patientName,

         boolean status
) {
}
