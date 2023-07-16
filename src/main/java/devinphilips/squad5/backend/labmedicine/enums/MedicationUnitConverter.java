package devinphilips.squad5.backend.labmedicine.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class MedicationUnitConverter implements AttributeConverter<MedicationUnit, String> {
    @Override
    public String convertToDatabaseColumn(MedicationUnit medicationUnit) {
        if(medicationUnit == null) {
            return null;
        }
        return medicationUnit.getCode();
    }

    @Override
    public MedicationUnit convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(MedicationUnit.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
