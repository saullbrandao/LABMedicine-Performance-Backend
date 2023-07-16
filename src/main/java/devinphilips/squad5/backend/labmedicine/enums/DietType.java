package devinphilips.squad5.backend.labmedicine.enums;

public enum DietType {
    LOWCARB("LOW CARB"),
    DASH("DASH"),
    PALEOLITICA("PALEOLITICA"),
    CETOGENICA("CETOGENICA"),
    DUKAN("DUKAN"),
    MEDITERRANEA("MEDITERRANEA"),
    OUTRA("OUTRA");

    private final String code;

    private DietType(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
