package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.PatientDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientPostRequest;
import devinphilips.squad5.backend.labmedicine.mappers.PatientMapper;
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

    public PatientDTO getById(int id) {
        return patientMapper.map(patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado")));
    }

    public List<PatientDTO> getAll() {
        return patientMapper.map(patientRepository.findAll());
    }

    public PatientDTO save(PatientPostRequest dto) {
        return patientMapper.map(patientRepository.save(patientMapper.map(dto)));
    }

    public void remove(int id) {
        patientRepository.deleteById(getById(id).getId());
    }
}
