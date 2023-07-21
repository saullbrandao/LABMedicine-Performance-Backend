package devinphilips.squad5.backend.labmedicine.dtos;

import devinphilips.squad5.backend.labmedicine.dtos.validators.CEP;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressPersistRequest {
    @CEP
    private String cep;

    @NotBlank
    private String street;

    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private String number;

    @NotBlank
    private String city;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String state;

    private String complement;

    private String referencePoint;
}
