package devinphilips.squad5.backend.labmedicine.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import devinphilips.squad5.backend.labmedicine.utils.EnumDeserializerUtils;

public enum DietType {
    LOWCARB,
    DASH,
    PALEOLITICA,
    CETOGENICA,
    DUKAN,
    MEDITERRANEA,
    OUTRA;

    @JsonCreator
    public static DietType getEnumValue(String value) {
        return EnumDeserializerUtils.deserializeEnum(value, DietType.class);
    }
}
