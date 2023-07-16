package devinphilips.squad5.backend.labmedicine.enums;

public enum MedicationUnit {
    MILIGRAMAS("mg"),
    MICROGRAMAS("mcg"),
    GRAMAS("g"),
    MILILITROS("mL"),
    PORCENTO("%");

    private final String code;

    private MedicationUnit(String code) {
        this.code = code;
    }

    public String getCode() { return code; }
}
