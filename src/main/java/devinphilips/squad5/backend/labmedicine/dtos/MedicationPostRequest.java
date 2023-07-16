package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicationPostRequest {
    private String name;

    private String date;

    private String time;

    private String type;

    private Double quantity;

    private String unit;

    private String observations;

    private Integer patientId;
}
