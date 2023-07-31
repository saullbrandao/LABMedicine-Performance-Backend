package devinphilips.squad5.backend.labmedicine.dtos;

import devinphilips.squad5.backend.labmedicine.dtos.validators.CEP;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressPersistRequestDTO {
    @CEP
    private String cep;

    @NotBlank(message = "Campo obrigatório.")
    private String street;

    @NotBlank(message = "Campo obrigatório.")
    @Pattern(regexp = "^[0-9]+$", message = "Deve conter apenas números.")
    private String number;

    @NotBlank(message = "Campo obrigatório.")
    private String city;

    @NotBlank(message = "Campo obrigatório.")
    private String neighborhood;

    @NotBlank(message = "Campo obrigatório.")
    private String state;

    private String complement;

    private String referencePoint;
}
