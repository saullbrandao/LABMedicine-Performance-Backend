package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient getById(Integer id) {
        return patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }

    public Patient getByPatientName(String name) {
        return patientRepository.findFirstByName(name).orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado"));
    }
}
