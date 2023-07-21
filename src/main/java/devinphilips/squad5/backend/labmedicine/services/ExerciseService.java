package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.ExerciseMapper;
import devinphilips.squad5.backend.labmedicine.models.Exercise;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;


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

    public ExerciseResponseDTO create(ExercisePostRequestDTO exercisePostRequestDTO) {
        Patient patient = patientService.getById(exercisePostRequestDTO.patientId());

        Exercise exercise = exerciseMapper.map(exercisePostRequestDTO);
        exercise.setPatient(patient);
        exercise.setStatus(true);
        Exercise savedExercise = exerciseRepository.save(exercise);

        return exerciseMapper.map(savedExercise);
    }

}
