package devinphilips.squad5.backend.labmedicine.dtos;

import devinphilips.squad5.backend.labmedicine.dtos.validators.Date;
import devinphilips.squad5.backend.labmedicine.dtos.validators.Phone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PatientPutRequestDTO {
    @NotBlank
    @Length(min = 8, max = 64)
    private String name;

    @NotBlank
    @Date
    private String birthDate;

    @NotBlank
    private String gender;

    @NotBlank
    private String maritalStatus;

    @NotBlank
    @Phone
    private String phone;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 64)
    private String naturality;

    @NotBlank
    @Phone
    private String emergencyContact;

    private String allergyList;

    private String specificCareList;

    private String healthInsurance;

    private Integer insuranceNumber;

    @Date
    private String insuranceValidity;

    @Valid
    private AddressPersistRequestDTO address;
}
