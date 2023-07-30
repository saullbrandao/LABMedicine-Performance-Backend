package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.mappers.DietMapper;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.models.Log;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.DietRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietService {
    private final DietRepository dietRepository;
    private final DietMapper dietMapper;
    private final PatientService patientService;
    private final UsersService usersService;
    private final LogService logService;

    public DietService(DietRepository dietRepository, DietMapper dietMapper, PatientService patientService, UsersService usersService, LogService logService) {
        this.dietRepository = dietRepository;
        this.dietMapper = dietMapper;
        this.patientService = patientService;
        this.usersService = usersService;
        this.logService = logService;
    }

    public List<DietResponseDTO> getAll() {
        return dietMapper.map(dietRepository.findAll());
    }

    public DietResponseDTO getById(Integer id) {
        return dietMapper.map(findById(id));
    }

    public List<DietResponseDTO> getByPatientName(String patientName) {
        Patient patient = patientService.getByPatientName(patientName);
        return dietMapper.map(dietRepository.findAllByPatient(patient));
    }

    public DietResponseDTO create(DietPostRequestDTO dietPostRequestDTO, String userEmail) {
        Patient patient = patientService.findById(dietPostRequestDTO.patientId());
        var user = usersService.getByEmail(userEmail);

        Diet diet = dietMapper.map(dietPostRequestDTO);
        diet.setPatient(patient);
        diet.setStatus(true);
        Diet savedDiet = dietRepository.save(diet);

        var log = new Log(
                String.format("O %s %s cadastrou uma dieta para o(a) paciente %s (%s)", user.type(), user.name(), patient.getName(), patient.getCpf())
        );

        logService.save(log);

        return dietMapper.map(savedDiet);
    }

    public DietResponseDTO update(Integer id, DietPutRequestDTO dietPutRequestDTO, String userEmail) {
        Diet existingDiet = findById(id);
        Patient patient = patientService.findById(existingDiet.getPatient().getId());
        var user = usersService.getByEmail(userEmail);

        existingDiet.setName(dietPutRequestDTO.name());
        existingDiet.setDietDate(dietPutRequestDTO.date());
        existingDiet.setDietTime(dietPutRequestDTO.time());
        existingDiet.setType(dietPutRequestDTO.type());
        existingDiet.setDescription(dietPutRequestDTO.description());
        existingDiet.setStatus(dietPutRequestDTO.status());

        Diet savedDiet = dietRepository.save(existingDiet);

        var log = new Log(
                String.format("O %s %s atualizou uma dieta (id: %s) do(a) paciente %s (%s)", user.type(), user.name(), savedDiet.getId(), patient.getName(), patient.getCpf())
        );

        logService.save(log);

        return dietMapper.map(savedDiet);
    }

    public void delete(Integer id, String userEmail) {
        Diet diet = findById(id);
        Patient patient = patientService.findById(diet.getPatient().getId());
        var user = usersService.getByEmail(userEmail);

        dietRepository.delete(diet);

        var log = new Log(
                String.format("O %s %s excluiu uma dieta (id: %s) do(a) paciente %s (%s)", user.type(), user.name(), diet.getId(), patient.getName(), patient.getCpf())
        );

        logService.save(log);
    }

    private Diet findById(Integer id) {
        return dietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dieta não encontrada com o ID: " + id));
    }
}
