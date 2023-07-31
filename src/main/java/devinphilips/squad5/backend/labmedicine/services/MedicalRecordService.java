package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.MedicalRecordDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.*;
import devinphilips.squad5.backend.labmedicine.repositories.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final ExamRepository examRepository;
    private final PatientService patientService;
    private final DietRepository dietRepository;
    private final MedicationRepository medicationRepository;
    private final AppointmentRepository appointmentRepository;
    private final ExerciseRepository exerciseRepository;
    private final MedicationMapper medicationMapper;
    private final ExerciseMapper exerciseMapper;
    private final DietMapper dietMapper;
    private final ExamMapper examMapper;
    private final AppointmentMapper appointmentMapper;

    public List<MedicalRecordDTO> getAllRecords(){
            List<PatientResponseDTO> patients = patientService.getAll();
            return patients.stream().map(
                   patient -> this.getById(patient.getId())
            ).collect(Collectors.toList());
    }

    public MedicalRecordDTO getById(Integer patientId){
        MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();

        medicalRecordDTO.setPatient(patientService.findById(patientId));
        medicalRecordDTO.setMedications(medicationMapper.map(medicationRepository.findAllByPatient(medicalRecordDTO.getPatient())));
        medicalRecordDTO.setExercises(exerciseMapper.map(exerciseRepository.findAllByPatient(medicalRecordDTO.getPatient())));
        medicalRecordDTO.setDiets(dietMapper.map(dietRepository.findAllByPatient(medicalRecordDTO.getPatient())));
        medicalRecordDTO.setExams(examMapper.map(examRepository.findAllByPatient(medicalRecordDTO.getPatient())));
        medicalRecordDTO.setAppointments(appointmentMapper.map(appointmentRepository.findAllByPatient(medicalRecordDTO.getPatient())));

        return  medicalRecordDTO;
    }
}
