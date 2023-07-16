package devinphilips.squad5.backend.labmedicine.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {
    @Override
    public String convertToDatabaseColumn(UserType userType) {
        if(userType == null) {
            return null;
        }
        return userType.getCode();
    }

    @Override
    public UserType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(UserType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
