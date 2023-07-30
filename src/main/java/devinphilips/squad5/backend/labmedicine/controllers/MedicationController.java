package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.MedicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicamentos")
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
            this.medicationService = medicationService;
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MedicationResponseDTO get(@PathVariable Integer id) {
        return medicationService.getById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<MedicationResponseDTO> get(@RequestParam(required = false) String patientName) {
        if (patientName == null || patientName.isBlank()) {
            return medicationService.getAll();
        }

        return medicationService.getByPatientName(patientName);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MedicationResponseDTO create(@RequestBody @Valid MedicationPostRequestDTO requestBody){
        return medicationService.create(requestBody);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MedicationResponseDTO update(@PathVariable Integer id, @RequestBody @Valid MedicationPutRequestDTO medicationPutRequestDTO) {
        return medicationService.update(id, medicationPutRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        medicationService.delete(id);
    }
}
