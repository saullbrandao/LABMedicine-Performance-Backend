package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.MedicationPostRequest;
import devinphilips.squad5.backend.labmedicine.enums.MedicationType;
import devinphilips.squad5.backend.labmedicine.enums.MedicationUnit;
import devinphilips.squad5.backend.labmedicine.models.Medication;
import devinphilips.squad5.backend.labmedicine.repositories.MedicationRepository;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/medicamentos")
public class MedicationController {
    private final PatientRepository patientRepository;
    private final MedicationRepository medicationRepository;

    public MedicationController(PatientRepository patientRepository, MedicationRepository medicationRepository) {
        this.patientRepository = patientRepository;
        this.medicationRepository = medicationRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        // logic below only for initial setup testings purpose
        return ResponseEntity.ok(medicationRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MedicationPostRequest requestBody){
        // logic below only for initial setup testings purpose
        var patient = patientRepository.findById(requestBody.getPatientId()).orElse(null);
        if(patient == null) return ResponseEntity.notFound().build();

        try {
            var medication = new Medication();
            medication.setPatient(patient);
            medication.setName(requestBody.getName());
            medication.setMedicationDate(LocalDateTime.parse(String.format("%sT%s:00", requestBody.getDate(), requestBody.getTime())));
            medication.setObservations(requestBody.getObservations());
            medication.setQuantity(requestBody.getQuantity());
            medication.setUnit(MedicationUnit.valueOf(requestBody.getUnit()));
            medication.setType(MedicationType.valueOf(requestBody.getType()));

            medicationRepository.save(medication);

            return ResponseEntity.ok().body(medication);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
