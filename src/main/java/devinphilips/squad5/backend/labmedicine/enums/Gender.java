package devinphilips.squad5.backend.labmedicine.enums;

public enum Gender {
    MASCULINO("MASCULINO"),
    FEMININO("FEMININO"),
    OUTRO("OUTRO");

    private final String code;

    private Gender(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
