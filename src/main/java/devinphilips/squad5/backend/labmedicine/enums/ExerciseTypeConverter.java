package devinphilips.squad5.backend.labmedicine.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ExerciseTypeConverter implements AttributeConverter<ExerciseType, String> {
    @Override
    public String convertToDatabaseColumn(ExerciseType exerciseType) {
        if(exerciseType == null) {
            return null;
        }
        return exerciseType.getCode();
    }

    @Override
    public ExerciseType convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return Stream.of(ExerciseType.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
