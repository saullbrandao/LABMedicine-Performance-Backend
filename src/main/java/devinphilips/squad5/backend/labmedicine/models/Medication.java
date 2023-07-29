package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.MedicationType;
import devinphilips.squad5.backend.labmedicine.enums.MedicationUnit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_MEDICATION", allocationSize = 1, name="my_seq_gen")
public class Medication extends BaseEntity {
    private String name;

    @Column(name = "medication_date")
    private LocalDateTime medicationDate;

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
