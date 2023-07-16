package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.ExamPostRequest;
import devinphilips.squad5.backend.labmedicine.models.Exam;
import devinphilips.squad5.backend.labmedicine.repositories.ExamRepository;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/exames")
public class ExamController {
    private final PatientRepository patientRepository;
    private final ExamRepository examRepository;

    public ExamController(PatientRepository patientRepository, ExamRepository examRepository) {
        this.patientRepository = patientRepository;
        this.examRepository = examRepository;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> getAll(){
        // logic below only for initial setup testings purpose
        return ResponseEntity.ok(examRepository.findAll());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> create(@RequestBody ExamPostRequest requestBody){
        var patient = patientRepository.findById(requestBody.getPatientId()).orElse(null);
        if(patient == null) return ResponseEntity.notFound().build();

        try {
            var exam = new Exam();
            exam.setStatus(true);
            exam.setPatient(patient);
            exam.setName(requestBody.getName());
            exam.setType(requestBody.getType());
            exam.setLaboratory(requestBody.getLaboratory());
            exam.setUrl(requestBody.getUrl());
            exam.setResults(requestBody.getResults());
            exam.setExamDate(LocalDateTime.parse(String.format("%sT%s:00", requestBody.getDate(), requestBody.getTime())));

            examRepository.save(exam);

            return ResponseEntity.created(URI.create("")).body(exam);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ocorreu algum erro");
        }
    }
}
