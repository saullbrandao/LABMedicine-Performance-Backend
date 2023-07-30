package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.ExerciseService;
import devinphilips.squad5.backend.labmedicine.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/exercicios")
public class ExerciseController {
    private final ExerciseService exerciseService;
    private final JwtService jwtService;

    public ExerciseController(ExerciseService exerciseService, JwtService jwtService) {
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ExerciseResponseDTO> get(@RequestParam(required = false) String patientName) {
        if (patientName == null || patientName.isBlank()) {
            return exerciseService.getAll();
        }

        return exerciseService.getByPatientName(patientName);
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ExerciseResponseDTO get(@PathVariable Integer id) {
        return exerciseService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ExerciseResponseDTO create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody @Valid ExercisePostRequestDTO exercisePostRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return exerciseService.create(exercisePostRequestDTO, userEmail);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ExerciseResponseDTO update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Integer id, @RequestBody @Valid ExercisePutRequestDTO exercisePutRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return exerciseService.update(id, exercisePutRequestDTO, userEmail);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Integer id) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        exerciseService.delete(id, userEmail);
    }
}
