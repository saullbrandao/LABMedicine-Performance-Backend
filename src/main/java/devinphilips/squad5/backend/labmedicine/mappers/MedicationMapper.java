package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.PatientPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationResponseDTO;

import devinphilips.squad5.backend.labmedicine.enums.MedicationType;
import devinphilips.squad5.backend.labmedicine.enums.MedicationUnit;
import devinphilips.squad5.backend.labmedicine.models.Medication;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicationMapper {


    @Mapping(target = "type", source="type", qualifiedByName ="stringToMedicationType")
    @Mapping(target = "unit", source="unit", qualifiedByName ="stringToMedicationUnit")
    Medication map(MedicationPostRequestDTO medication);
    MedicationResponseDTO map(Medication source);

    @Mapping(target = "type", source="type", qualifiedByName ="stringToMedicationType")
    @Mapping(target = "unit", source="unit", qualifiedByName ="stringToMedicationUnit")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Medication updateExisting(MedicationPutRequestDTO source, @MappingTarget Medication medication);

    List<MedicationResponseDTO> map(List<Medication> source);

    static MedicationType stringToMedicationType(String value) {
        return MedicationType.valueOf(value.toUpperCase());
    }
    static MedicationUnit stringToMedicationUnit(String value) {
        return MedicationUnit.valueOf(value.toUpperCase());
    }



}
