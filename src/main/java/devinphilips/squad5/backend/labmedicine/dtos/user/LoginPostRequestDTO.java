package devinphilips.squad5.backend.labmedicine.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginPostRequestDTO(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
