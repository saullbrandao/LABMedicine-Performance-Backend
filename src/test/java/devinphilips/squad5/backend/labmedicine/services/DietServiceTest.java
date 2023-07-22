package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.enums.DietType;
import devinphilips.squad5.backend.labmedicine.mappers.DietMapper;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.DietRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DietServiceTest {
    @Mock
    private DietRepository dietRepository;

    @Mock
    private DietMapper dietMapper;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private DietService dietService;

    private static final Integer PATIENT_ID = 1;

    private Patient mockPatient;
    private List<Diet> mockDiets;
    private List<DietResponseDTO> mockDietResponses;

    @BeforeEach
    void setUp() {
        mockPatient = createMockPatient();
        mockDiets = createMockDiets(5);
        mockDietResponses = createMockDietResponses(5);
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {
        @Test
        @DisplayName("Should return list of diet response DTOs when diets exist")
        void whenDietsExist() {
            when(dietRepository.findAll()).thenReturn(mockDiets);
            when(dietMapper.map(mockDiets)).thenReturn(mockDietResponses);

            List<DietResponseDTO> result = dietService.getAll();

            Assertions.assertEquals(mockDietResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no diets exist")
        void whenNoDiets() {
            when(dietRepository.findAll()).thenReturn(Collections.emptyList());

            List<DietResponseDTO> result = dietService.getAll();

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getByPatientName")
    class GetByPatientName {
        @Test
        @DisplayName("Should return list of diet response DTOs for given patient name")
        void whenDietsExistForPatientName() {
            String patientName = "John Doe";
            mockPatient.setName(patientName);

            when(patientService.getByPatientName(patientName)).thenReturn(mockPatient);
            when(dietRepository.findAllByPatient(mockPatient)).thenReturn(mockDiets);
            when(dietMapper.map(mockDiets)).thenReturn(mockDietResponses);

            List<DietResponseDTO> result = dietService.getByPatientName(patientName);

            Assertions.assertEquals(mockDietResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no diets found for given patient name")
        void whenNoDietsForPatientName() {
            String patientName = "John Doe";
            mockPatient.setName(patientName);

            when(patientService.getByPatientName(patientName)).thenReturn(mockPatient);
            when(dietRepository.findAllByPatient(mockPatient)).thenReturn(Collections.emptyList());

            List<DietResponseDTO> result = dietService.getByPatientName(patientName);

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should create new diet")
        void createDiet() {
            DietPostRequestDTO mockDietRequest = createMockDietPostRequest(DietType.LOWCARB);
            Diet mockDiet = createMockDiet(1);
            DietResponseDTO mockDietResponse = createMockDietResponse(1);

            when(patientService.findById(PATIENT_ID)).thenReturn(mockPatient);
            when(dietMapper.map(any(DietPostRequestDTO.class))).thenReturn(mockDiet);
            when(dietRepository.save(any(Diet.class))).thenReturn(mockDiet);
            when(dietMapper.map(any(Diet.class))).thenReturn(mockDietResponse);

            DietResponseDTO result = dietService.create(mockDietRequest);

            verify(patientService).findById(PATIENT_ID);
            verify(dietRepository).save(any(Diet.class));
            Assertions.assertEquals(mockDietResponse, result);
            Assertions.assertTrue(result.status());
        }

        @ParameterizedTest
        @EnumSource(DietType.class)
        @DisplayName("Should create new diet with different diet types")
        void createDiet_withDifferentTypes(DietType dietType) {
            DietPostRequestDTO mockDietRequest = createMockDietPostRequest(dietType);
            Diet mockDiet = createMockDiet(1);
            DietResponseDTO mockDietResponse = createMockDietResponse(1);

            when(patientService.findById(PATIENT_ID)).thenReturn(mockPatient);
            when(dietMapper.map(any(DietPostRequestDTO.class))).thenReturn(mockDiet);
            when(dietRepository.save(any(Diet.class))).thenReturn(mockDiet);
            when(dietMapper.map(any(Diet.class))).thenReturn(mockDietResponse);

            DietResponseDTO result = dietService.create(mockDietRequest);

            verify(patientService).findById(PATIENT_ID);
            verify(dietRepository).save(any(Diet.class));
            Assertions.assertEquals(mockDietResponse, result);
            Assertions.assertTrue(result.status());
        }


        private DietPostRequestDTO createMockDietPostRequest(DietType dietType) {
            return new DietPostRequestDTO(
                    "Test Name",
                    LocalDate.of(2023, 5, 10),
                    LocalTime.of(10, 0),
                    dietType,
                    "Test Description",
                    PATIENT_ID
            );
        }
    }

    @Nested
    @DisplayName(("update"))
    class Update {
        @Test
        @DisplayName("Should throw exception when diet not found")
        void notFound() {
            DietPutRequestDTO mockRequest = mock(DietPutRequestDTO.class);

            when(dietRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> dietService.update(anyInt(), mockRequest));
        }


        @Test
        @DisplayName("Should update diet when the diet exists")
        void found() {
            Integer dietId = 1;
            DietPutRequestDTO dietPutRequestDTO = new DietPutRequestDTO(
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    DietType.CETOGENICA,
                    "Updated Description"
            );

            Diet existingDiet = Diet.builder()
                    .id(dietId)
                    .name("Old Name")
                    .dietDate(LocalDate.of(2023, 8, 10))
                    .dietTime(LocalTime.of(10, 0))
                    .type(DietType.LOWCARB)
                    .description("Old Description")
                    .build();

            Diet savedDiet = Diet.builder()
                    .id(dietId)
                    .name("Updated Name")
                    .dietDate(LocalDate.of(2023, 8, 15))
                    .dietTime(LocalTime.of(11, 30))
                    .type(DietType.CETOGENICA)
                    .description("Updated Description")
                    .build();

            DietResponseDTO expectedResponse = new DietResponseDTO(
                    dietId,
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    DietType.LOWCARB,
                    "Updated Description",
                    true,
                    null
            );

            when(dietRepository.findById(dietId)).thenReturn(Optional.of(existingDiet));
            when(dietRepository.save(any(Diet.class))).thenReturn(savedDiet);
            when(dietMapper.map(savedDiet)).thenReturn(expectedResponse);

            DietResponseDTO result = dietService.update(dietId, dietPutRequestDTO);

            verify(dietRepository).findById(dietId);
            verify(dietRepository).save(any(Diet.class));
            verify(dietMapper).map(savedDiet);

            Assertions.assertEquals(dietPutRequestDTO.name(), savedDiet.getName());
            Assertions.assertEquals(dietPutRequestDTO.date(), savedDiet.getDietDate());
            Assertions.assertEquals(dietPutRequestDTO.time(), savedDiet.getDietTime());
            Assertions.assertEquals(dietPutRequestDTO.type(), savedDiet.getType());
            Assertions.assertEquals(dietPutRequestDTO.description(), savedDiet.getDescription());
            Assertions.assertEquals(expectedResponse, result);
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("Should throw exception when diet not found")
        void notFound() {
            when(dietRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> dietService.delete(anyInt()));
        }

        @Test
        @DisplayName("Should delete diet when the diet exists")
        void found() {
            Diet mockDiet = mock(Diet.class);
            when(dietRepository.findById(anyInt())).thenReturn(Optional.of(mockDiet));

            dietService.delete(anyInt());

            verify(dietRepository).delete(mockDiet);
        }
    }

    private Patient createMockPatient() {
        Patient mockPatient = new Patient();
        mockPatient.setId(PATIENT_ID);
        return mockPatient;
    }

    private Diet createMockDiet(Integer id) {
        return Diet.builder()
                .id(id)
                .name("Test Name")
                .dietDate(LocalDate.of(2023, 5, 10))
                .dietTime(LocalTime.of(10, 0))
                .type(DietType.LOWCARB)
                .description("Test Description")
                .patient(createMockPatient())
                .build();
    }

    private DietResponseDTO createMockDietResponse(Integer id) {
        return new DietResponseDTO(
                id,
                "Test Name",
                LocalDate.of(2023, 5, 10),
                LocalTime.of(10, 0),
                DietType.LOWCARB,
                "Test Description",
                true,
                PATIENT_ID
        );
    }

    private List<Diet> createMockDiets(int count) {
        List<Diet> diets = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            diets.add(createMockDiet(i));
        }
        return diets;
    }

    private List<DietResponseDTO> createMockDietResponses(int count) {
        List<DietResponseDTO> dietResponses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            dietResponses.add(createMockDietResponse(i));
        }
        return dietResponses;
    }
}
