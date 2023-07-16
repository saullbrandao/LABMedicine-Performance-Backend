package devinphilips.squad5.backend.labmedicine.enums;

public enum ExerciseType {
    RESISTENCIA_AEROBICA("RESISTENCIA AEROBICA"),
    RESISTENCIA_MUSCULAR("RESISTENCIA MUSCULAR"),
    FLEXIBILIDADE("FLEXIBILIDADE"),
    FORCA("FORCA"),
    AGILIDADE("AGILIDADE"),
    OUTRO("OUTRO");

    private final String code;

    private ExerciseType(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
