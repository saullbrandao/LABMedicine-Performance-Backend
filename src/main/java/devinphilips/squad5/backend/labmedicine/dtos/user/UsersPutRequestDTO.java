package devinphilips.squad5.backend.labmedicine.dtos.user;

import devinphilips.squad5.backend.labmedicine.dtos.validators.Phone;
import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UsersPutRequestDTO(
        @NotNull
        UserType type,

        @NotBlank
        @Length(min = 8, max = 64)
        String name,

        @NotNull
        Gender gender,

        @NotBlank
        @Phone
        String phone,

        @NotBlank
        @Length(min = 6)
        String password
) {
}
