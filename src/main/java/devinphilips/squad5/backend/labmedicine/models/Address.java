package devinphilips.squad5.backend.labmedicine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@SequenceGenerator(sequenceName = "SQ_ADDRESS", allocationSize = 1, name = "my_seq_gen")
public class Address extends BaseEntity {
    private String cep;

    private String street;

    @Column(name = "num")
    private String number;

    private String city;

    private String neighborhood;

    private String state;

    private String complement;

    @Column(name = "reference_point")
    private String referencePoint;
}
