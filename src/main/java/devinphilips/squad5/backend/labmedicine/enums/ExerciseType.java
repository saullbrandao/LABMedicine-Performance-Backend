package devinphilips.squad5.backend.labmedicine.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import devinphilips.squad5.backend.labmedicine.utils.EnumDeserializerUtils;

public enum ExerciseType {
    RESISTENCIA_AEROBICA,
    RESISTENCIA_MUSCULAR,
    FLEXIBILIDADE,
    FORCA,
    AGILIDADE,
    OUTRO;

    @JsonCreator
    public static ExerciseType getEnumValue(String value) {
        return EnumDeserializerUtils.deserializeEnum(value, ExerciseType.class);
    }
}
