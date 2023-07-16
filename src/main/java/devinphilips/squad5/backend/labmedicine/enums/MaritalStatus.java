package devinphilips.squad5.backend.labmedicine.enums;

public enum MaritalStatus {
    SOLTEIRO("SOLTEIRO"),
    CASADO("CASADO"),
    SEPARADO("SEPARADO"),
    VIUVO("VIUVO");

    private final String code;

    private MaritalStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
