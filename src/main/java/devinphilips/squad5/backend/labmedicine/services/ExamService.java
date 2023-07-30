package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.ExamMapper;
import devinphilips.squad5.backend.labmedicine.models.Exam;
import devinphilips.squad5.backend.labmedicine.models.Log;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.ExamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
    private final ExamRepository examRepository;
    private final PatientService patientService;
    private final UsersService usersService;
    private final ExamMapper examMapper;
    private final LogService logService;

    public ExamService(PatientService patientService,
                       ExamMapper examMapper,
                       ExamRepository examRepository, UsersService usersService, LogService logService) {
        this.patientService = patientService;
        this.examMapper = examMapper;
        this.examRepository = examRepository;
        this.usersService = usersService;
        this.logService = logService;
    }

    public ExamResponseDTO create(ExamPostRequestDTO examPostRequestDTO, String userEmail) {
        Patient patient = patientService.findById(examPostRequestDTO.patientId());
        var user = usersService.getByEmail(userEmail);

        Exam exam = examMapper.map(examPostRequestDTO);
        exam.setPatient(patient);
        exam.setStatus(true);
        Exam savedExam = examRepository.save(exam);

        logService.registerCreate(user, patient, "um exame");

        return examMapper.map(savedExam);
    }

    public ExamResponseDTO update(Integer id, ExamPutRequestDTO examPutRequestDTO, String userEmail) {
        Exam existingExam = findById(id);
        Patient patient = patientService.findById(existingExam.getPatient().getId());
        var user = usersService.getByEmail(userEmail);

        existingExam.setName(examPutRequestDTO.name());
        existingExam.setExamDate(
                examPutRequestDTO.date().atTime(examPutRequestDTO.time()));
        existingExam.setStatus(examPutRequestDTO.status());
        existingExam.setType(examPutRequestDTO.type());
        existingExam.setLaboratory(examPutRequestDTO.laboratory());
        existingExam.setResults(examPutRequestDTO.results());

        Exam savedExam = examRepository.save(existingExam);

        logService.registerUpdate(user, patient, savedExam.getId(), "um exame");

        return examMapper.map(savedExam);
    }

    public Exam findById(Integer id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exame não encontrado com o ID: " + id));
    }

    public List<ExamResponseDTO> getAll(String patientName) {
        if (patientName == null || patientName.isBlank()) {
            return examMapper.map(examRepository.findAll());
        }
        Patient patient = patientService.getByPatientName(patientName);
        return examMapper.map(examRepository.findAllByPatient(patient));
    }

    public ExamResponseDTO getById(Integer id) {
        return examMapper.map(examRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Exame não encontrado com o ID: " + id)));
    }

    public void delete(Integer id, String userEmail) {
        Exam exam = findById(id);
        Patient patient = patientService.findById(exam.getPatient().getId());
        var user = usersService.getByEmail(userEmail);

        examRepository.delete(exam);

        logService.registerDelete(user, patient, exam.getId(), "um exame");
    }
}

