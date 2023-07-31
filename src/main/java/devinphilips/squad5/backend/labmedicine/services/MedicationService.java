package devinphilips.squad5.backend.labmedicine.services;




import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.medication.MedicationResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.MedicationMapper;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.models.Medication;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;
    private final PatientService patientService;

   public MedicationService( MedicationRepository medicationRepository,
                             MedicationMapper medicationMapper,
                             PatientService patientService
                             ){
       this.medicationRepository = medicationRepository;
       this.medicationMapper = medicationMapper;
       this.patientService = patientService;

   }

    public MedicationResponseDTO getById(Integer id) {
        return medicationMapper.map(findById(id));
    }

    public List<MedicationResponseDTO> getAll() {
        return medicationMapper.map(medicationRepository.findAll());
    }

    public List<MedicationResponseDTO> getByPatientName(String patientName) {
        Patient patient = patientService.getByPatientName(patientName);
        return medicationMapper.map(medicationRepository.findAllByPatient(patient));
    }

    public MedicationResponseDTO update(Integer id, MedicationPutRequestDTO medicationPutRequestDTO ) {
        Medication medication = medicationMapper.updateExisting(medicationPutRequestDTO, findById(id));
        return medicationMapper.map(medicationRepository.save(medication));
    }
    public void delete(Integer id) {
        Medication medication = findById(id);
        medicationRepository.delete(medication);
    }
   public MedicationResponseDTO create(MedicationPostRequestDTO dto){
       Patient patient = patientService.findById(dto.patientId()) ;

       Medication medication = medicationMapper.map(dto);
       medication.setPatient(patient);
       medication.setStatus(true);

       return medicationMapper.map(medicationRepository.save(medication));
   }


    private Medication findById(Integer id) {
        return medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicação não encontrada com o ID: " + id));
    }


}
