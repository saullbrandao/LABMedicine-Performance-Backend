package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.StatisticDTO;
import devinphilips.squad5.backend.labmedicine.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class StatiscService {

    private final ExamRepository examRepository;
    private final PatientRepository patientRepository;
    private final DietRepository dietRepository;
    private final MedicationRepository medicationRepository;
    private final AppointmentRepository appointmentRepository;
    private final ExerciseRepository exerciseRepository;

    public StatiscService(ExamRepository examRepository, PatientRepository patientRepository, DietRepository dietRepository, MedicationRepository medicationRepository, AppointmentRepository appointmentRepository, ExerciseRepository exerciseRepository) {
        this.examRepository = examRepository;
        this.patientRepository = patientRepository;
        this.dietRepository = dietRepository;
        this.medicationRepository = medicationRepository;
        this.appointmentRepository = appointmentRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public StatisticDTO getStats(){
        StatisticDTO statisticDTO = new StatisticDTO();

        statisticDTO.setPatient(patientRepository.count());
        statisticDTO.setExam(examRepository.count());
        statisticDTO.setAppointment(appointmentRepository.count());
        statisticDTO.setDiet(dietRepository.count());
        statisticDTO.setExam(examRepository.count());
        statisticDTO.setMedication(medicationRepository.count());
        statisticDTO.setExercise(exerciseRepository.count());


        return  statisticDTO;

    }
}
