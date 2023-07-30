package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.ExerciseMapper;
import devinphilips.squad5.backend.labmedicine.models.Exercise;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final PatientService patientService;
    private final UsersService usersService;
    private final ExerciseMapper exerciseMapper;
    private final LogService logService;

    public ExerciseService(ExerciseRepository exerciseRepository, PatientService patientService, UsersService usersService, ExerciseMapper exerciseMapper, LogService logService) {
        this.exerciseRepository = exerciseRepository;
        this.patientService = patientService;
        this.usersService = usersService;
        this.exerciseMapper = exerciseMapper;
        this.logService = logService;
    }

    public List<ExerciseResponseDTO> getAll() {
        return exerciseMapper.map(exerciseRepository.findAll());
    }

    public ExerciseResponseDTO getById(Integer id) {
        return exerciseMapper.map(findById(id));
    }


    public List<ExerciseResponseDTO> getByPatientName(String patientName) {
        Patient patient = patientService.getByPatientName(patientName);
        return exerciseMapper.map(exerciseRepository.findAllByPatient(patient));
    }

    public ExerciseResponseDTO create(ExercisePostRequestDTO exercisePostRequestDTO, String userEmail) {
        Patient patient = patientService.findById(exercisePostRequestDTO.patientId());
        var user = usersService.getByEmail(userEmail);

        Exercise exercise = exerciseMapper.map(exercisePostRequestDTO);
        exercise.setPatient(patient);
        exercise.setStatus(true);
        Exercise savedExercise = exerciseRepository.save(exercise);

        logService.registerCreate(user, patient, "um exercício");


        return exerciseMapper.map(savedExercise);
    }

    public ExerciseResponseDTO update(Integer id, ExercisePutRequestDTO exercisePutRequestDTO, String userEmail) {
        Exercise existingExercise = findById(id);
        Patient patient = patientService.findById(existingExercise.getPatient().getId());
        var user = usersService.getByEmail(userEmail);

        existingExercise.setName(exercisePutRequestDTO.name());
        existingExercise.setExerciseDate(exercisePutRequestDTO.date());
        existingExercise.setExerciseTime(exercisePutRequestDTO.time());
        existingExercise.setType(exercisePutRequestDTO.type());
        existingExercise.setAmountPerWeek(exercisePutRequestDTO.amountPerWeek());
        existingExercise.setDescription(exercisePutRequestDTO.description());
        existingExercise.setStatus(exercisePutRequestDTO.status());

        Exercise savedExercise = exerciseRepository.save(existingExercise);

        logService.registerUpdate(user, patient, savedExercise.getId(), "um exercício");

        return exerciseMapper.map(savedExercise);
    }

    public void delete(Integer id, String userEmail) {
        Exercise exercise = findById(id);
        Patient patient = patientService.findById(exercise.getPatient().getId());
        var user = usersService.getByEmail(userEmail);

        exerciseRepository.delete(exercise);

        logService.registerDelete(user, patient, exercise.getId(), "um exercício");
    }

    private Exercise findById(Integer id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercício não encontrado com o ID: " + id));
    }
}
