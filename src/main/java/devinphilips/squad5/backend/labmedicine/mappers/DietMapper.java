package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DietMapper {
    @Mapping(target = "dietDate", source = "date")
    @Mapping(target = "dietTime", source = "time")
    Diet map(DietPostRequestDTO diet);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientName", source = "patient.name")
    @Mapping(target = "date", source = "dietDate")
    @Mapping(target = "time", source = "dietTime")
    DietResponseDTO map(Diet diet);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "date", source = "dietDate")
    @Mapping(target = "time", source = "dietTime")
    List<DietResponseDTO> map(List<Diet> diet);
}
