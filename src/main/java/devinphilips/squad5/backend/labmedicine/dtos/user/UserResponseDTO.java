package devinphilips.squad5.backend.labmedicine.dtos.user;

import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.UserType;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserType type,
        String name,
        Gender gender,
        String cpf,
        String phone,
        String email
) {
}
