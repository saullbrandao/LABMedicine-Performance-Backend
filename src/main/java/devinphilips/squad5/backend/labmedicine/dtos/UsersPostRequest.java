package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersPostRequest {
    private String type;

    private String name;

    private String gender;

    private String cpf;

    private String phone;

    private String email;

    private String password;
}
