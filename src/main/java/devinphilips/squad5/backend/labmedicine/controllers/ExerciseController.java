package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/exercicios")
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ExerciseResponseDTO> get(@RequestParam(required = false) String patientName) {
        if (patientName.isBlank()) {
            return exerciseService.getAll();
        }

        return exerciseService.getByPatientName(patientName);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ExerciseResponseDTO create(@RequestBody @Valid ExercisePostRequestDTO exercisePostRequestDTO) {
        return exerciseService.create(exercisePostRequestDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ExerciseResponseDTO update(@PathVariable Integer id, @RequestBody @Valid ExercisePutRequestDTO exercisePutRequestDTO) {
        return exerciseService.update(id, exercisePutRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        exerciseService.delete(id);
    }
}
