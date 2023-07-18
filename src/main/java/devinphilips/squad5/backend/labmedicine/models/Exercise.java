package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.ExerciseType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_EXERCISE", allocationSize = 1, name="my_seq_gen")
public class Exercise extends BaseEntity {
    private String name;

    private LocalDateTime exerciseDate;

    @Enumerated(EnumType.STRING)
    private ExerciseType type;

    @Column(name = "amount_per_week")
    private Integer amountPerWeek;

    private String description;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
