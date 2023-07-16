package devinphilips.squad5.backend.labmedicine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_APPOINTMENT", allocationSize = 1, name="my_seq_gen")
public class Appointment extends BaseEntity {
    private String reason;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    private String description;

    private String medication;

    @Column(name = "dosage_and_precautions")
    private String dosageAndPrecautions;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
