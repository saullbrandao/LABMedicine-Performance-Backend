package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.AppointmentPostRequest;
import devinphilips.squad5.backend.labmedicine.models.Appointment;
import devinphilips.squad5.backend.labmedicine.repositories.AppointmentRepository;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/consultas")
public class AppointmentController {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentController(PatientRepository patientRepository, AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        // logic below only for initial setup testings purpose
        return ResponseEntity.ok(appointmentRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AppointmentPostRequest requestBody){
        // logic below only for initial setup testings purpose
        try {
            var patient = patientRepository.findById(requestBody.getPatientId()).orElse(null);
            if(patient == null) return ResponseEntity.notFound().build();

            var now = LocalDateTime.now();

            var appointment = new Appointment();
            appointment.setDescription(requestBody.getDescription());
            appointment.setPatient(patient);
            appointment.setReason(requestBody.getReason());
            appointment.setStatus(true);
            appointment.setDosageAndPrecautions(requestBody.getDosageAndPrecautions());
            appointment.setMedication(requestBody.getMedication());
            appointment.setAppointmentDate(LocalDateTime.parse(String.format("%sT%s:00", requestBody.getDate(), requestBody.getTime())));
            appointment.setCreatedAt(now);
            appointment.setUpdatedAt(now);

            appointmentRepository.save(appointment);

            return ResponseEntity.created(URI.create("")).body(appointment);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
