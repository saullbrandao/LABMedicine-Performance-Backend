package devinphilips.squad5.backend.labmedicine.dtos.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO (
        Integer id,
        String reason,
        LocalDate date,
        LocalTime time,
        String description,
        String medication,
        String dosageAndPrecautions,
        Boolean status,
        Integer patientId,
        String patientName
) {
}
