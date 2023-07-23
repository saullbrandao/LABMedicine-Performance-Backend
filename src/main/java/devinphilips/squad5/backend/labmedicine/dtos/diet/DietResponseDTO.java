package devinphilips.squad5.backend.labmedicine.dtos.diet;

import devinphilips.squad5.backend.labmedicine.enums.DietType;

import java.time.LocalDate;
import java.time.LocalTime;

public record DietResponseDTO(
        Integer id,
        String name,
        LocalDate date,
        LocalTime time,
        DietType type,
        String description,
        Boolean status,
        Integer patientId,
        String patientName
) {
}
