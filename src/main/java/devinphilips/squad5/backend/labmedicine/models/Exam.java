package devinphilips.squad5.backend.labmedicine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_EXAM", allocationSize = 1, name="my_seq_gen")
public class Exam extends BaseEntity {
    private String name;

    @Column(name = "exam_date")
    private LocalDateTime examDate;

    private String type;

    private String laboratory;

    private String url;

    private String results;

    private boolean status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
