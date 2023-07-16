package devinphilips.squad5.backend.labmedicine.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class MaritalStatusConverter implements AttributeConverter<MaritalStatus, String> {
    @Override
    public String convertToDatabaseColumn(MaritalStatus maritalStatus) {
        if(maritalStatus == null) {
            return null;
        }
        return maritalStatus.getCode();
    }

    @Override
    public MaritalStatus convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(MaritalStatus.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
