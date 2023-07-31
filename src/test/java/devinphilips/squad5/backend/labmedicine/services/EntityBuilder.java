package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.AddressResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.UserResponseDTO;
import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.MaritalStatus;
import devinphilips.squad5.backend.labmedicine.enums.UserType;
import devinphilips.squad5.backend.labmedicine.models.Address;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.models.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityBuilder {
    static Users buildUser(int id) {
        return Users.builder()
                .id(id)
                .createdAt(LocalDateTime.now())
                .name("Any Name")
                .cpf("00000000000")
                .build();
    }

    static UserResponseDTO buildUserResposeDTO(int id) {
        return new UserResponseDTO(
                id,
                LocalDateTime.now(),
                LocalDateTime.now(),
                UserType.ADMIN,
                "Any Name",
                Gender.MASCULINO,
                "00000000000",
                "51994565485",
                "any_email@any.com"
        );
    }

    static Address buildAddress(int id){
        return Address.builder()
                .id(id)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .cep("90100200")
                .city("Any City")
                .neighborhood("Any Neighborhood")
                .number("100")
                .state("Any State")
                .street("Any Street")
                .build();
    }

    static Patient buildPatient(int id) {
        return Patient.builder()
                .id(id)
                .cpf("12345678912")
                .rg("123456 AAA/II XX")
                .name("Any Name")
                .email("any@email.com")
                .allergyList("any allergy")
                .birthDate(LocalDate.parse("1990-10-15"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .emergencyContact("51996154568")
                .phone("51994584516")
                .gender(Gender.MASCULINO)
                .maritalStatus(MaritalStatus.CASADO)
                .naturality("Any Place")
                .status(true)
                .address(buildAddress(id))
                .healthInsurance("Any Health Insurance")
                .insuranceNumber(123456)
                .insuranceValidity(LocalDate.parse("2025-12-31"))
                .build();
    }

    static AddressResponseDTO buildAdressResponseDTO(int id) {
        var address = buildAddress(id);
        return new AddressResponseDTO(
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getCity(),
                address.getNeighborhood(),
                address.getState(),
                address.getComplement(),
                address.getReferencePoint()
        );
    }

    static PatientResponseDTO buildPatientResponseDTO(int id) {
        var patient = buildPatient(id);
        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getCpf(),
                patient.getRg(),
                patient.getBirthDate().toString(),
                patient.getGender().toString(),
                patient.getMaritalStatus().toString(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getNaturality(),
                patient.getEmergencyContact(),
                patient.getAllergyList(),
                patient.getSpecificCareList(),
                patient.getHealthInsurance(),
                patient.getInsuranceNumber(),
                patient.getInsuranceValidity().toString(),
                patient.isStatus(),
                buildAdressResponseDTO(id)
        );
    }

    static List<Patient> buildListOfPatients(int count){
        var patients = new ArrayList<Patient>();
        for(int i = 1; i <= count; i++) {
            patients.add(buildPatient(i));
        }
        return patients;
    }

    static List<PatientResponseDTO> buildListOfPatientResponseDTOs(int count){
        return buildListOfPatients(count).stream().map(p -> buildPatientResponseDTO(p.getId())).toList();
    }
}
