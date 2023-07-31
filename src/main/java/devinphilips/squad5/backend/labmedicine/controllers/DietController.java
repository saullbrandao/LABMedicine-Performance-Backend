package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.DietService;
import devinphilips.squad5.backend.labmedicine.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dietas")
public class DietController {
    private final DietService dietService;
    private final JwtService jwtService;

    public DietController(DietService dietService, JwtService jwtService) {
        this.dietService = dietService;
        this.jwtService = jwtService;
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
    public DietResponseDTO create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody @Valid DietPostRequestDTO dietPostRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return dietService.create(dietPostRequestDTO, userEmail);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DietResponseDTO update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                  @PathVariable Integer id,
                                  @RequestBody @Valid DietPutRequestDTO dietPutRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return dietService.update(id, dietPutRequestDTO, userEmail);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Integer id) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        dietService.delete(id, userEmail);
    }
}
