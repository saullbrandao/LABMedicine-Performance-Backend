package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponseDTO {
    private int id;

    private String name;

    private String cpf;

    private String rg;

    private String birthDate;

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

    private boolean status;

    private AddressResponseDTO address;
}
