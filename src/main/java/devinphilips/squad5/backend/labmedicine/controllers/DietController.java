package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.enums.DietType;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.services.DietService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/dietas")
public class DietController {
    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public DietResponseDTO create(@RequestBody @Valid DietPostRequestDTO dietPostRequestDTO) {
        return dietService.create(dietPostRequestDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DietResponseDTO update(@PathVariable Integer id, @RequestBody @Valid DietPutRequestDTO dietPutRequestDTO) {
        return dietService.update(id, dietPutRequestDTO);
    }
}
