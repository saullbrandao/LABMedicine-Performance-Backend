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

    public PatientService(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
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

    public PatientResponseDTO create(PatientPostRequestDTO dto) {
        return patientMapper.map(patientRepository.save(patientMapper.map(dto)));
    }

    public void remove(int id) {
        patientRepository.deleteById(getById(id).getId());
    }

    public void update(int id, PatientPutRequestDTO dto) {
        var updatedPatient = patientMapper.updateExisting(dto, findById(id));
        patientRepository.save(updatedPatient);
    }

    public Patient findById(int id) {
        return patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }

    public Patient getByPatientName(String name) {
        return patientRepository.findFirstByName(name).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }
}
