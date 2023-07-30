package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.MedicalRecordDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientResponseDTO;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    private final ExamRepository examRepository;
    private final PatientService patientService;
    private final DietRepository dietRepository;
    private final MedicationRepository medicationRepository;
    private final AppointmentRepository appointmentRepository;
    private final ExerciseRepository exerciseRepository;

    public MedicalRecordService(ExamRepository examRepository, PatientService patientService, DietRepository dietRepository, MedicationRepository medicationRepository, AppointmentRepository appointmentRepository, ExerciseRepository exerciseRepository) {
        this.examRepository = examRepository;
        this.patientService = patientService;
        this.dietRepository = dietRepository;
        this.medicationRepository = medicationRepository;
        this.appointmentRepository = appointmentRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public List<MedicalRecordDTO> getAllRecords(){
            List<PatientResponseDTO> patients = patientService.getAll();
            List<MedicalRecordDTO> medicalRecords = patients.stream().map(
                   patient -> {
                       return this.getById(patient.getId());
                   }
            ).collect(Collectors.toList());

            return  medicalRecords;
    }

    public MedicalRecordDTO getById(Integer patientId){
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();

        medicalRecordDTO.setPatient(patientService.findById(patientId));
        medicalRecordDTO.setMedications(medicationRepository.findAllByPatient(medicalRecordDTO.getPatient()));
        medicalRecordDTO.setExercises(exerciseRepository.findAllByPatient(medicalRecordDTO.getPatient()));
        medicalRecordDTO.setDiets(dietRepository.findAllByPatient(medicalRecordDTO.getPatient()));
        medicalRecordDTO.setExams(examRepository.findAllByPatient(medicalRecordDTO.getPatient()));
        medicalRecordDTO.setAppointments(appointmentRepository.findAllByPatient(medicalRecordDTO.getPatient()));


        return  medicalRecordDTO;

    }
}
