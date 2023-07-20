package devinphilips.squad5.backend.labmedicine.utils;

import java.util.EnumSet;

public class EnumDeserializerUtils {

    public static <T extends Enum<T>> T deserializeEnum(String value, Class<T> enumClass) {
        for (T enumValue : EnumSet.allOf(enumClass)) {
            if (enumValue.name().equals(value)) {
                return enumValue;
            }
        }
        return null;
    }
}
