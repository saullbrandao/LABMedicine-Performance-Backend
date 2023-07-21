package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String cep;

    private String street;

    private String number;

    private String city;

    private String neighborhood;

    private String state;

    private String complement;

    private String referencePoint;
}
