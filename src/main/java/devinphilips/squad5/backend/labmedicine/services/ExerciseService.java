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
    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exerciseRepository, PatientService patientService, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.patientService = patientService;
        this.exerciseMapper = exerciseMapper;
    }

    public List<ExerciseResponseDTO> getAll() {
        return exerciseMapper.map(exerciseRepository.findAll());
    }

    public List<ExerciseResponseDTO> getByPatientName(String patientName) {
        Patient patient = patientService.getByPatientName(patientName);
        return exerciseMapper.map(exerciseRepository.findAllByPatient(patient));
    }

    public ExerciseResponseDTO create(ExercisePostRequestDTO exercisePostRequestDTO) {
        Patient patient = patientService.findById(exercisePostRequestDTO.patientId());

        Exercise exercise = exerciseMapper.map(exercisePostRequestDTO);
        exercise.setPatient(patient);
        exercise.setStatus(true);
        Exercise savedExercise = exerciseRepository.save(exercise);

        return exerciseMapper.map(savedExercise);
    }

    public ExerciseResponseDTO update(Integer id, ExercisePutRequestDTO exercisePutRequestDTO) {
        Exercise existingExercise = findById(id);

        existingExercise.setName(exercisePutRequestDTO.name());
        existingExercise.setExerciseDate(exercisePutRequestDTO.date());
        existingExercise.setExerciseTime(exercisePutRequestDTO.time());
        existingExercise.setType(exercisePutRequestDTO.type());
        existingExercise.setAmountPerWeek(exercisePutRequestDTO.amountPerWeek());
        existingExercise.setDescription(exercisePutRequestDTO.description());

        Exercise savedExercise = exerciseRepository.save(existingExercise);

        return exerciseMapper.map(savedExercise);
    }

    public void delete(Integer id) {
        Exercise exercise = findById(id);
        exerciseRepository.delete(exercise);
    }

    private Exercise findById(Integer id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercício não encontrado com o ID: " + id));
    }
}
