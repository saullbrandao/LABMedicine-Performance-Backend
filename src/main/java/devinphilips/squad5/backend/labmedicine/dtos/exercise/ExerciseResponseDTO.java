package devinphilips.squad5.backend.labmedicine.dtos.exercise;

import devinphilips.squad5.backend.labmedicine.enums.ExerciseType;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExerciseResponseDTO(
        Integer id,
        String name,
        LocalDate date,
        LocalTime time,
        ExerciseType type,
        Integer amountPerWeek,
        String description,
        Boolean status,
        Integer patientId
) {
}
