package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.user.UserResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.UsersPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.UsersPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.models.Users;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {
    Users map(UsersPostRequestDTO source);

    UserResponseDTO map(Users source);

    List<UserResponseDTO> map(List<Users> source);

    @Mappings({
            @Mapping(target = "cpf", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    Users map(UsersPutRequestDTO source, @MappingTarget Users user);
}
