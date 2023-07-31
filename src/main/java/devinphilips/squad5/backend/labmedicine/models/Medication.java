package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.MedicationType;
import devinphilips.squad5.backend.labmedicine.enums.MedicationUnit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@SequenceGenerator(sequenceName = "SQ_MEDICATION", allocationSize = 1, name="my_seq_gen")
public class Medication extends BaseEntity {
    private String name;

    private LocalDate medicationDate;

    private LocalTime medicationTime;

    @Enumerated(EnumType.STRING)
    private MedicationType type;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private MedicationUnit unit;

    private String observations;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
