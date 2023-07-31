package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationResponseDTO;

import devinphilips.squad5.backend.labmedicine.models.Medication;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicationMapper {
    @Mapping(target = "medicationDate", source = "date")
    @Mapping(target = "medicationTime", source = "time")
    Medication map(MedicationPostRequestDTO medication);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientName", source = "patient.name")
    @Mapping(target = "date", source = "medicationDate")
    @Mapping(target = "time", source = "medicationTime")
    MedicationResponseDTO map(Medication source);

    @Mapping(target = "medicationDate", source = "date")
    @Mapping(target = "medicationTime", source = "time")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medication updateExisting(MedicationPutRequestDTO source, @MappingTarget Medication medication);

    List<MedicationResponseDTO> map(List<Medication> source);
}
