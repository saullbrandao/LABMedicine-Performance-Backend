package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DietPostRequest {
    private String name;

    private String date;

    private String time;

    private String type;

    private String description;

    private Integer patientId;
}
