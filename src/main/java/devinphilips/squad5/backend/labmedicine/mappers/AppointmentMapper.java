package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.models.Appointment;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    @Mapping(target = "appointmentDate",
            expression = "java(concatenateLocalDateAndTime(appointment.date(), appointment.time()))")
    Appointment map(AppointmentPostRequestDTO appointment);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "date", expression = "java(extractDateFromLocalDateTime(appointment.getAppointmentDate()))")
    @Mapping(target = "time", expression = "java(extractTimeFromLocalDateTime(appointment.getAppointmentDate()))")
    @Mapping(target = "medication", source = "medication")
    AppointmentResponseDTO map(Appointment appointment);

    @Mapping(target = "appointmentDate",
            expression = "java(concatenateLocalDateAndTime(appointment.date(), appointment.time()))")
    List<AppointmentResponseDTO> map(List<Appointment> appointment);


    default LocalDateTime concatenateLocalDateAndTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            return null;
        }
        return LocalDateTime.of(date, time);
    }

    default LocalDate extractDateFromLocalDateTime(LocalDateTime appointmentDate) {
        if (appointmentDate == null) {
            return null;
        }
        return appointmentDate.toLocalDate();
    }

    default LocalTime extractTimeFromLocalDateTime(LocalDateTime appointmentDate) {
        if (appointmentDate == null) {
            return null;
        }
        return appointmentDate.toLocalTime();
    }
}

