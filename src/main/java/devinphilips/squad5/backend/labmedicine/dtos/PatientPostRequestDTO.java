package devinphilips.squad5.backend.labmedicine.dtos;

import devinphilips.squad5.backend.labmedicine.dtos.validators.Date;
import devinphilips.squad5.backend.labmedicine.dtos.validators.Phone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class PatientPostRequestDTO {
    @NotBlank(message = "Campo obrigatório.")
    @Length(min = 8, max = 64, message = "Mínimo 8 e máximo 64 caracteres.")
    private String name;

    @NotNull(message = "Campo obrigatório.")
    @CPF(message = "Valor inválido.")
    private String cpf;

    @NotBlank(message = "Campo obrigatório.")
    @Length(max = 20, message = "Máximo 20 caracteres.")
    private String rg;

    @NotBlank(message = "Campo obrigatório.")
    @Date
    private String birthDate;

    @NotBlank(message = "Campo obrigatório.")
    private String gender;

    @NotBlank(message = "Campo obrigatório.")
    private String maritalStatus;

    @NotBlank(message = "Campo obrigatório.")
    @Phone
    private String phone;

    @NotBlank(message = "Campo obrigatório.")
    @Email(message = "Formato inválido.")
    private String email;

    @NotBlank(message = "Campo obrigatório.")
    @Length(min = 8, max = 64, message = "Mínimo 8 e máximo 64 caracteres.")
    private String naturality;

    @NotBlank(message = "Campo obrigatório.")
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
