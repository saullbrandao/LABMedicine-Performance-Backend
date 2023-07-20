package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.models.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExerciseMapper {
    @Mapping(target = "exerciseDate", source = "date")
    @Mapping(target = "exerciseTime", source = "time")
    Exercise map(ExercisePostRequestDTO exercise);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "date", source = "exerciseDate")
    @Mapping(target = "time", source = "exerciseTime")
    ExerciseResponseDTO map(Exercise exercise);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "date", source = "exerciseDate")
    @Mapping(target = "time", source = "exerciseTime")
    List<ExerciseResponseDTO> map(List<Exercise> exercises);
}
