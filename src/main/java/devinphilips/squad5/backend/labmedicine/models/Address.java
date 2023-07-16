package devinphilips.squad5.backend.labmedicine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ADDRESS")
    @SequenceGenerator(
            name = "SQ_ADDRESS",
            sequenceName = "SQ_ADDRESS",
            allocationSize = 1
    )
    private int id;

    private String cep;

    private String street;

    private String num;

    private String city;

    private String neighborhood;

    private String state;

    private String complement;

    @Column(name = "reference_point")
    private String referencePoint;
}
