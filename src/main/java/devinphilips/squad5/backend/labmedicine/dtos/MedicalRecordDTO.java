package devinphilips.squad5.backend.labmedicine.dtos;

import devinphilips.squad5.backend.labmedicine.models.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MedicalRecordDTO {
    private Patient patient;
    private List<Exam> exams;
    private List<Appointment> appointments;
    private List<Diet> diets;
    private List<Exercise> exercises;
    private List<Medication> medications;

}
