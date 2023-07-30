package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private Long patient;
    private Long exam;
    private Long appointment;
    private Long exercise;
    private Long diet;
    private Long medication;
}
