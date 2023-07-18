package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.DietType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_DIET", allocationSize = 1, name="my_seq_gen")
public class Diet extends BaseEntity {
    private String name;

    private LocalDateTime dietDate;

    @Enumerated(EnumType.STRING)
    private DietType type;

    private String description;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
