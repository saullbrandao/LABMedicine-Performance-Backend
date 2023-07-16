package devinphilips.squad5.backend.labmedicine.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class DietTypeConverter implements AttributeConverter<DietType, String> {
    @Override
    public String convertToDatabaseColumn(DietType dietType) {
        if(dietType == null) {
            return null;
        }
        return dietType.getCode();
    }

    @Override
    public DietType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(DietType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
