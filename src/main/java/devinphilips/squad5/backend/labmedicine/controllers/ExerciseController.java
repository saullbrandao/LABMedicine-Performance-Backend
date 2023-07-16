package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.ExercisePostRequest;
import devinphilips.squad5.backend.labmedicine.enums.ExerciseType;
import devinphilips.squad5.backend.labmedicine.models.Exercise;
import devinphilips.squad5.backend.labmedicine.repositories.ExerciseRepository;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/exercicios")
public class ExerciseController {
    private final PatientRepository patientRepository;
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(PatientRepository patientRepository, ExerciseRepository exerciseRepository) {
        this.patientRepository = patientRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(exerciseRepository.findAll());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> create(@RequestBody ExercisePostRequest requestBody) {
        var patient = patientRepository.findById(requestBody.getPatientId()).orElse(null);
        if(patient == null) return ResponseEntity.notFound().build();

        try {
            var exercise = new Exercise();
            exercise.setPatient(patient);
            exercise.setExerciseDate(LocalDateTime.parse(String.format("%sT%s:00", requestBody.getDate(), requestBody.getTime())));
            exercise.setName(requestBody.getName());
            exercise.setStatus(true);
            exercise.setType(ExerciseType.valueOf(requestBody.getType()));
            exercise.setDescription(requestBody.getDescription());
            exercise.setAmountPerWeek(requestBody.getAmountPerWeek());

            exerciseRepository.save(exercise);

            return ResponseEntity.ok().body(exercise);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
