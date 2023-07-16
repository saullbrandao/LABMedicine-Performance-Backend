package devinphilips.squad5.backend.labmedicine.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class MedicationTypeConverter implements AttributeConverter<MedicationType, String> {
    @Override
    public String convertToDatabaseColumn(MedicationType medicationType) {
        if(medicationType == null) {
            return null;
        }
        return medicationType.getCode();
    }

    @Override
    public MedicationType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(MedicationType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
