package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.PatientPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam("name") Optional<String> value) {
        var name = value.orElse(null);
        return name == null
                ? ResponseEntity.ok().body(patientService.getAll())
                : ResponseEntity.ok().body(patientService.getAllByName(name));
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return ResponseEntity.ok().body(patientService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid PatientPostRequestDTO requestBody) {
        return ResponseEntity.created(URI.create("")).body(patientService.create(requestBody));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> remove(@PathVariable int id) {
        patientService.remove(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody @Valid PatientPutRequestDTO requestBody) {
        patientService.update(id, requestBody);
        return ResponseEntity.accepted().build();
    }
}
