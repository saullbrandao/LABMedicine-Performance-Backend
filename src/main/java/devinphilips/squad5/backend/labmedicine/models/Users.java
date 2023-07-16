package devinphilips.squad5.backend.labmedicine.models;

import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SequenceGenerator(sequenceName = "SQ_USERS", allocationSize = 1, name="my_seq_gen")
public class Users extends BaseEntity {

    private UserType type;

    private String name;

    private Gender gender;

    private String cpf;

    private String phone;

    private String email;

    private String password;
}
