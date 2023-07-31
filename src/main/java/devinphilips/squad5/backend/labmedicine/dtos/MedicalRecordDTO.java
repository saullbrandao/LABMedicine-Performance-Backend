package devinphilips.squad5.backend.labmedicine.dtos;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationResponseDTO;
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
    private List<ExamResponseDTO> exams;
    private List<AppointmentResponseDTO> appointments;
    private List<DietResponseDTO> diets;
    private List<ExerciseResponseDTO> exercises;
    private List<MedicationResponseDTO> medications;

}
