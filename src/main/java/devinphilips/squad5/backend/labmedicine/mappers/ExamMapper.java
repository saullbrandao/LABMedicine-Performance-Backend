package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamResponseDTO;
import devinphilips.squad5.backend.labmedicine.models.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExamMapper {
    @Mapping(target = "examDate",expression = "java(concatenateLocalDateAndTime(exam.date(), exam.time()))")
    Exam map(ExamPostRequestDTO exam);
    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "date", expression = "java(extractDateFromLocalDateTime(exam.getExamDate()))")
    @Mapping(target = "time", expression = "java(extractTimeFromLocalDateTime(exam.getExamDate()))")
    ExamResponseDTO map(Exam exam);

    @Mapping(target = "examDate",
            expression = "java(concatenateLocalDateAndTime(exam.date(), exam.time()))")
    List<ExamResponseDTO> map(List<Exam> exam);



    default LocalDateTime concatenateLocalDateAndTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) {
            return null;
        }
        return LocalDateTime.of(date, time);
    }

    default LocalDate extractDateFromLocalDateTime(LocalDateTime examDate) {
        if (examDate == null) {
            return null;
        }
        return examDate.toLocalDate();
    }

    default LocalTime extractTimeFromLocalDateTime(LocalDateTime examDate) {
        if (examDate == null) {
            return null;
        }
        return examDate.toLocalTime();
    }

}

