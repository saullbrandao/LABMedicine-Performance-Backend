package devinphilips.squad5.backend.labmedicine.dtos.exam;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExamResponseDTO (
        Integer id,
        String name,
        LocalDate date,
        LocalTime time,
        String type,
        String laboratory,
        String url,
        String results,
        Boolean status,
        Integer patientId
) {
}
