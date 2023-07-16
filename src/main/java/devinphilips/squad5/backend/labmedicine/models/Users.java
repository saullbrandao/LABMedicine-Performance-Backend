package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USERS")
    @SequenceGenerator(
            name = "SQ_USERS",
            sequenceName = "SQ_USERS",
            allocationSize = 1
    )
    private int id;

    private UserType type;

    private String name;

    private Gender gender;

    private String cpf;

    private String phone;

    private String email;

    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
