package devinphilips.squad5.backend.labmedicine.dtos.user;

import devinphilips.squad5.backend.labmedicine.dtos.validators.Phone;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

public record UsersPostRequestDTO (
       @NotBlank
       String type,

       @NotBlank
       @Length(min = 8, max = 64)
       String name,

       @NotBlank
       String gender,

       @NotBlank
       @CPF
       String cpf,

       @NotBlank
       @Phone
       String phone,

       @NotBlank
       String email,

       @NotBlank
       @Length(min = 6)
       String password
) {
}
