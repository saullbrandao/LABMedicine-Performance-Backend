package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentPostRequest {
    private String reason;

    private String date;

    private String time;

    private String description;

    private String medication;

    private String dosageAndPrecautions;

    private Integer patientId;
}
