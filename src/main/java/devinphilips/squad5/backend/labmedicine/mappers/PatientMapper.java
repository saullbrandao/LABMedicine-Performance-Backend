package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.*;
import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.MaritalStatus;
import devinphilips.squad5.backend.labmedicine.models.Address;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {
    @Mapping(target = "cpf", source = "cpf", qualifiedByName = "sanitizeCPF")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToGender")
    @Mapping(target = "maritalStatus", source = "maritalStatus", qualifiedByName = "stringToMaritalStatus")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "phoneToOnlyNumbers")
    @Mapping(target = "emergencyContact", source = "emergencyContact", qualifiedByName = "phoneToOnlyNumbers")
    Patient map(PatientPostRequest source);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToGender")
    @Mapping(target = "maritalStatus", source = "maritalStatus", qualifiedByName = "stringToMaritalStatus")
    @Mapping(target = "phone", source = "phone", qualifiedByName = "phoneToOnlyNumbers")
    @Mapping(target = "emergencyContact", source = "emergencyContact", qualifiedByName = "phoneToOnlyNumbers")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Patient updateExisting(PatientPutRequest source, @MappingTarget Patient patient);

    @Mapping(target = "cep", source = "cep", qualifiedByName = "sanitizeCEP")
    void updateExisting(AddressPersistRequest source, @MappingTarget Address address);

    PatientDTO map(Patient source);

    List<PatientDTO> map(List<Patient> source);

    @Mapping(target = "cep", source = "cep", qualifiedByName = "sanitizeCEP")
    Address map(AddressPersistRequest source);

    AddressDTO map(Address source);

    @Named("sanitizeCPF")
    static String sanitizeCPF(String value) {
        return value.replaceAll("[\\.-]", "");
    }

    @Named("stringToGender")
    static Gender stringToGender(String value) {
        return Gender.valueOf(value.toUpperCase());
    }

    @Named("stringToMaritalStatus")
    static MaritalStatus stringToMaritalStatus(String value) {
        return MaritalStatus.valueOf(value.toUpperCase());
    }

    @Named("phoneToOnlyNumbers")
    static String phoneToOnlyNumbers(String value) {
        return value.replaceAll("[ \\-)(]", "");
    }

    @Named("sanitizeCEP")
    static String sanitizeCEP(String value) {
        return value.replace("-", "");
    }
}
