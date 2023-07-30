package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.PatientResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.mappers.PatientMapper;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final UsersService usersService;
    private final LogService logService;

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper, UsersService usersService, LogService logService) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.usersService = usersService;
        this.logService = logService;
    }

    public PatientResponseDTO getById(int id) {
        return patientMapper.map(findById(id));
    }

    public List<PatientResponseDTO> getAll() {
        return patientMapper.map(patientRepository.findAll());
    }

    public List<PatientResponseDTO> getAllByName(String name) {
        return patientMapper.map(patientRepository.findByNameContainingIgnoreCase(name));
    }

    public PatientResponseDTO create(PatientPostRequestDTO dto, String userEmail) {
        var newPatient = patientMapper.map(dto);
        newPatient.setStatus(true);

        var user = usersService.getByEmail(userEmail);

        logService.registerPatientCreate(user, newPatient);

        return patientMapper.map(patientRepository.save(newPatient));
    }

    public void remove(int id, String userEmail) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) return;
        var user = usersService.getByEmail(userEmail);

        patientRepository.deleteById(getById(id).getId());

        logService.registerPatientDelete(user, patient);
    }

    public void update(int id, PatientPutRequestDTO dto, String userEmail) {
        var updatedPatient = patientMapper.updateExisting(dto, findById(id));
        var user = usersService.getByEmail(userEmail);

        patientRepository.save(updatedPatient);

        logService.registerPatientUpdate(user, updatedPatient);
    }

    public Patient findById(int id) {
        return patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }

    public Patient getByPatientName(String name) {
        return patientRepository.findFirstByName(name).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }
}
