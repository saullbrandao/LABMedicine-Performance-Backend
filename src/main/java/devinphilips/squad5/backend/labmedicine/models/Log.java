package devinphilips.squad5.backend.labmedicine.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq_gen")
    @SequenceGenerator(sequenceName = "SQ_LOG", allocationSize = 1, name = "log_seq_gen")
    private int id;

    private LocalDateTime createdAt;

    private String action;

    public Log(String action) {
        this.action = action;
    }

    @PrePersist
    private void setTimestamp() {
        createdAt = LocalDateTime.now();
    }
}
