package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.MedicalRecordDTO;
import devinphilips.squad5.backend.labmedicine.services.MedicalRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/prontuarios")
public class MedicalRecordController {
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public List<MedicalRecordDTO> getAll(){
        return medicalRecordService.getAllRecords();
    }

    @GetMapping("/{id}")
    public MedicalRecordDTO getByid(@PathVariable Integer id){
        return medicalRecordService.getById(id);
    }
}
