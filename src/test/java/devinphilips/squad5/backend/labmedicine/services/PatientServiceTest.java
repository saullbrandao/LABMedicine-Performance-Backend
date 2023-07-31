package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.PatientPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.PatientPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.user.UserResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.PatientMapper;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Component
public class PatientServiceTest {
    // region Fields and Constructor
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @Mock
    private UsersService usersService;

    @Mock
    private LogService logService;

    @InjectMocks
    private PatientService patientService;

    private final Patient mockPatient;
    private final PatientResponseDTO mockPatientResponseDTO;

    private final List<Patient> mockPatients;

    private final List<PatientResponseDTO> mockPatientResponses;

    public PatientServiceTest() {
        mockPatient = createMockPatient();
        mockPatientResponseDTO = createMockPatientResponseDTO(mockPatient.getId());
        mockPatients = createMockPatients(5);
        mockPatientResponses = createMockPatientResponses(5);
    }
    // endregion

    @Nested
    @DisplayName("getAll")
    class GetAll {
        @Test
        @DisplayName("Should call patientRepository.findAll")
        void callsPatientRepositoryFindAll(){
            when(patientRepository.findAll()).thenReturn(Collections.emptyList());

            patientService.getAll();

            verify(patientRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return list of patients")
        void patientsScenario(){
            when(patientRepository.findAll()).thenReturn(mockPatients);
            when(patientMapper.map(mockPatients)).thenReturn(mockPatientResponses);

            var result = patientService.getAll();

            Assertions.assertEquals(mockPatientResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no patients exist")
        void noPatientsScenario() {
            when(patientRepository.findAll()).thenReturn(Collections.emptyList());

            var result = patientService.getAll();

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getById")
    class GetById {
        @Test
        @DisplayName("Should call patientRepository.findById")
        void callsPatientRepositoryFindById() {
            when(patientRepository.findById(anyInt())).thenReturn(Optional.of(new Patient()));
            when(patientMapper.map(any(Patient.class))).thenReturn(mockPatientResponseDTO);

            patientService.getById(1);

            verify(patientRepository, times(1)).findById(anyInt());
        }

        @Test
        @DisplayName("Should return the patient when found")
        void patientFoundScenario() {
            when(patientRepository.findById(anyInt())).thenReturn(Optional.of(new Patient()));
            when(patientMapper.map(any(Patient.class))).thenReturn(createMockPatientResponseDTO(anyInt()));

            var result = patientService.getById(1);

            Assertions.assertEquals(result.getClass(), PatientResponseDTO.class);
            Assertions.assertEquals(0, result.getId());
        }

        @Test
        @DisplayName("Should throw when no patient found")
        void patientNotFoundScenario() {
            Assertions.assertThrows(EntityNotFoundException.class, () -> patientService.getById(1));
        }
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should call patientRepository.save")
        void callsPatientRepositorySave() {
            when(usersService.getByEmail(anyString())).thenReturn(EntityBuilder.buildUserResposeDTO(1));
            when(patientMapper.map(any(PatientPostRequestDTO.class))).thenReturn(new Patient());
            when(patientMapper.map(any(Patient.class))).thenReturn(mockPatientResponseDTO);
            when(patientRepository.save(any(Patient.class))).thenReturn(mockPatient);

            patientService.create(new PatientPostRequestDTO(), "devs@labmedicine.com");

            verify(patientRepository, times(1)).save(any(Patient.class));
        }

        @Test
        @DisplayName("Should create new Patient")
        void createPatient() {
            when(usersService.getByEmail(anyString())).thenReturn(EntityBuilder.buildUserResposeDTO(1));
            when(patientMapper.map(any(PatientPostRequestDTO.class))).thenReturn(new Patient());
            when(patientMapper.map(any(Patient.class))).thenReturn(mockPatientResponseDTO);
            when(patientRepository.save(any(Patient.class))).thenReturn(mockPatient);

            var result = patientService.create(new PatientPostRequestDTO(), "devs@labmedicine.com");

            Assertions.assertNotNull(result);
            Assertions.assertEquals(mockPatientResponseDTO, result);
        }
    }

    @Nested
    @DisplayName("update")
    class Update {
        @Test
        @DisplayName("Should throw exception if patient not found")
        void patientNotFoundScenario() {
            when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());
            Assertions.assertThrows(EntityNotFoundException.class, () -> patientService.update(1, mock(PatientPutRequestDTO.class), "devs@labmedicine.com"));
        }

        @Test
        @DisplayName("Should call patientRepository.save")
        void callsPatientRepositorySave() {
            when(usersService.getByEmail(anyString())).thenReturn(EntityBuilder.buildUserResposeDTO(1));
            when(patientRepository.findById(anyInt())).thenReturn(Optional.of(mockPatient));
            when(patientMapper.updateExisting(any(PatientPutRequestDTO.class), any(Patient.class))).thenReturn(mockPatient);

            patientService.update(1, new PatientPutRequestDTO(), "devs@labmedicine.com");

            verify(patientRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {
        @Test
        @DisplayName("Should throw exception if patient not found")
        void patientNotFoundScenario() {
            when(patientRepository.findById(anyInt())).thenReturn(Optional.empty());
            Assertions.assertThrows(EntityNotFoundException.class, () -> patientService.remove(anyInt(), "devs@labmedicine.com"));
        }

        @Test
        @DisplayName("Should call patientRepository.deleteById")
        void callsPatientRepository() {
            when(usersService.getByEmail(anyString())).thenReturn(EntityBuilder.buildUserResposeDTO(1));
            when(patientRepository.findById(anyInt())).thenReturn(Optional.of(mock(Patient.class)));
            when(patientMapper.map(any(Patient.class))).thenReturn(mock(PatientResponseDTO.class));

            patientService.remove(anyInt(), "devs@labmedicine.com");

            verify(patientRepository, times(1)).deleteById(anyInt());
        }
    }

    // region Private methods
    private Patient createMockPatient(){
        return EntityBuilder.buildPatient(1);
    }

    private PatientResponseDTO createMockPatientResponseDTO(int id) {
        return EntityBuilder.buildPatientResponseDTO(id);
    }

    private List<Patient> createMockPatients(int count){
        return EntityBuilder.buildListOfPatients(count);
    }

    private List<PatientResponseDTO> createMockPatientResponses(int count){
        return EntityBuilder.buildListOfPatientResponseDTOs(count);
    }
    // endregion
}
