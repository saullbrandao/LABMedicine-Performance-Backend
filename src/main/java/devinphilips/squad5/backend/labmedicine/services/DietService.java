package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.DietMapper;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.DietRepository;
import org.springframework.stereotype.Service;

@Service
public class DietService {
    private final DietRepository dietRepository;
    private final DietMapper dietMapper;
    private final PatientService patientService;

    public DietService(DietRepository dietRepository, DietMapper dietMapper, PatientService patientService) {
        this.dietRepository = dietRepository;
        this.dietMapper = dietMapper;
        this.patientService = patientService;
    }


    public DietResponseDTO create(DietPostRequestDTO dietPostRequestDTO) {
        Patient patient = patientService.getById(dietPostRequestDTO.patientId());

        Diet diet = dietMapper.map(dietPostRequestDTO);
        diet.setPatient(patient);
        diet.setStatus(true);
        Diet savedDiet = dietRepository.save(diet);

        return dietMapper.map(savedDiet);
    }
}
