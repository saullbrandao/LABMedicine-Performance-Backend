package devinphilips.squad5.backend.labmedicine.mappers;

import devinphilips.squad5.backend.labmedicine.dtos.user.UsersPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.models.Users;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapper {
    Users map(UsersPostRequestDTO source);
}
