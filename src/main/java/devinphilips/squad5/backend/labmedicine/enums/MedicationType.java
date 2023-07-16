package devinphilips.squad5.backend.labmedicine.enums;

public enum MedicationType {
    CAPSULA("CAPSULA"),
    COMPRIMIDO("COMPRIMIDO"),
    LIQUIDO("LIQUIDO"),
    CREME("CREME"),
    GEL("GEL"),
    INALACAO("INALACAO"),
    INJECAO("INJECAO"),
    SPRAY("SPRAY");

    private final String code;

    private MedicationType(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
