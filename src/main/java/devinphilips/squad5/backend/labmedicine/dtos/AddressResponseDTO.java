package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {
    private String cep;

    private String street;

    private String number;

    private String city;

    private String neighborhood;

    private String state;

    private String complement;

    private String referencePoint;
}
