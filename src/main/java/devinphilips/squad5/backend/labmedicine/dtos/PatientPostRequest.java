package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientPostRequest {
    private String name;

    private String cpf;

    private String rg;

    private String dateOfBirth;

    private String gender;

    private String maritalStatus;

    private String phone;

    private String email;

    private String naturality;

    private String emergencyContact;

    private String allergyList;

    private String specificCareList;

    private String healthInsurance;

    private Integer insuranceNumber;

    private String insuranceValidity;

    private AddressPostRequest address;
}
