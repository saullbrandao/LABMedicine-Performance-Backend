package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.PatientPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.services.JwtService;
import devinphilips.squad5.backend.labmedicine.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PatientController {
    private final PatientService patientService;
    private final JwtService jwtService;

    public PatientController(PatientService patientService, JwtService jwtService) {
        this.patientService = patientService;
        this.jwtService = jwtService;
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
    public ResponseEntity<?> create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody @Valid PatientPostRequestDTO requestBody) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return ResponseEntity.created(URI.create("")).body(patientService.create(requestBody, userEmail));
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> remove(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable int id) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        patientService.remove(id, userEmail);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id:\\d+}")
    public ResponseEntity<?> update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable int id, @RequestBody @Valid PatientPutRequestDTO requestBody) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        patientService.update(id, requestBody, userEmail);
        return ResponseEntity.accepted().build();
    }
}
