package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.PatientPostRequest;
import devinphilips.squad5.backend.labmedicine.enums.Gender;
import devinphilips.squad5.backend.labmedicine.enums.MaritalStatus;
import devinphilips.squad5.backend.labmedicine.models.Address;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pacientes")
public class PatientController {
    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getAll() {
        try {
            var patients = repository.findAll();
            return ResponseEntity.ok().body(patients);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ocorreu algum erro");
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> create(@RequestBody PatientPostRequest requestBody){
        try{
            var now = LocalDateTime.now();

            var address = new Address();
            address.setCep(requestBody.getAddress().getCep());
            address.setComplement(requestBody.getAddress().getComplement());
            address.setCity(requestBody.getAddress().getCity());
            address.setNum(requestBody.getAddress().getNumber());
            address.setNeighborhood(requestBody.getAddress().getNeighborhood());
            address.setState(requestBody.getAddress().getState());
            address.setStreet(requestBody.getAddress().getStreet());

            var patient = new Patient();
            patient.setName(requestBody.getName());
            patient.setCpf(requestBody.getCpf());
            patient.setRg(requestBody.getRg());
            patient.setBirthDate(LocalDate.parse(requestBody.getDateOfBirth()));
            patient.setGender(Gender.valueOf(requestBody.getGender()));
            patient.setMaritalStatus(MaritalStatus.valueOf(requestBody.getMaritalStatus()));
            patient.setPhone(requestBody.getPhone());
            patient.setEmail(requestBody.getEmail());
            patient.setNaturality(requestBody.getNaturality());
            patient.setEmergencyContact(requestBody.getEmergencyContact());
            patient.setStatus(true);
            patient.setCreatedAt(now);
            patient.setUpdatedAt(now);
            patient.setAddress(address);

            repository.save(patient);

            return ResponseEntity.created(URI.create("")).body(patient);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().body("Ocorreu algum erro");
        }
    }
}
