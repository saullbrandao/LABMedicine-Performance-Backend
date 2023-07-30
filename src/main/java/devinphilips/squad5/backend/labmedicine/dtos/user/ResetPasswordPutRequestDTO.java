package devinphilips.squad5.backend.labmedicine.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ResetPasswordPutRequestDTO(
        @Email
        @NotBlank
        String email,

        @NotBlank
        @Length(min = 6)
        String currentPassword,

        @NotBlank
        @Length(min = 6)
        String newPassword
) {
}
