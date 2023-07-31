package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamPostRequest {
    private String name;

    private String date;

    private String time;

    private String type;

    private String laboratory;

    private String url;

    private String results;

    private Integer patientId;
}
