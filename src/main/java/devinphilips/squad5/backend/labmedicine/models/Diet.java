package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.DietType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(sequenceName = "SQ_DIET", allocationSize = 1, name = "my_seq_gen")
public class Diet extends BaseEntity {
    private String name;

    private LocalDate dietDate;

    private LocalTime dietTime;

    @Enumerated(EnumType.STRING)
    private DietType type;

    private String description;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
