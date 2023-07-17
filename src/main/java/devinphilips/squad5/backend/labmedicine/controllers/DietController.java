package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.DietPostRequest;
import devinphilips.squad5.backend.labmedicine.enums.DietType;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.repositories.DietRepository;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/dietas")
public class DietController {
    private final PatientRepository patientRepository;
    private final DietRepository dietRepository;

    public DietController(PatientRepository patientRepository, DietRepository dietRepository) {
        this.patientRepository = patientRepository;
        this.dietRepository = dietRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        // logic below only for initial setup testings purpose
        return ResponseEntity.ok(dietRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DietPostRequest requestBody){
        // logic below only for initial setup testings purpose
        var patient = patientRepository.findById(requestBody.getPatientId()).orElse(null);
        if(patient == null) return ResponseEntity.notFound().build();

        try{
            var diet = new Diet();
            diet.setName(requestBody.getName());
            diet.setDietDate(LocalDateTime.parse(String.format("%sT%s:00", requestBody.getDate(), requestBody.getTime())));
            diet.setType(DietType.valueOf(requestBody.getType()));
            diet.setPatient(patient);
            diet.setStatus(true);
            diet.setDescription(requestBody.getDescription());

            dietRepository.save(diet);

            return ResponseEntity.ok().body(diet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
