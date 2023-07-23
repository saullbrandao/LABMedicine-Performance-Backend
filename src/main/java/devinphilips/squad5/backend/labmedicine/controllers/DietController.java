package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.DietService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dietas")
public class DietController {
    private final DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<DietResponseDTO> get(@RequestParam(required = false) String patientName) {
        if (patientName == null || patientName.isBlank()) {
            return dietService.getAll();
        }

        return dietService.getByPatientName(patientName);
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DietResponseDTO get(@PathVariable Integer id) {
        return dietService.getById(id);
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

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        dietService.delete(id);
    }
}
