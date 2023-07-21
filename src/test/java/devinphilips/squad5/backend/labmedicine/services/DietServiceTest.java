package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.diet.DietResponseDTO;
import devinphilips.squad5.backend.labmedicine.dtos.diet.DietPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.enums.DietType;
import devinphilips.squad5.backend.labmedicine.mappers.DietMapper;
import devinphilips.squad5.backend.labmedicine.models.Diet;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.DietRepository;
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
import java.util.List;

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
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should create new diet")
        void createDiet() {
            DietPostRequestDTO mockDietRequest = createMockDietPostRequest(DietType.LOWCARB);
            Diet mockDiet = createMockDiet(1);
            DietResponseDTO mockDietResponse = createMockDietResponse(1);

            when(patientService.getById(PATIENT_ID)).thenReturn(mockPatient);
            when(dietMapper.map(any(DietPostRequestDTO.class))).thenReturn(mockDiet);
            when(dietRepository.save(any(Diet.class))).thenReturn(mockDiet);
            when(dietMapper.map(any(Diet.class))).thenReturn(mockDietResponse);

            DietResponseDTO result = dietService.create(mockDietRequest);

            verify(patientService).getById(PATIENT_ID);
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

            when(patientService.getById(PATIENT_ID)).thenReturn(mockPatient);
            when(dietMapper.map(any(DietPostRequestDTO.class))).thenReturn(mockDiet);
            when(dietRepository.save(any(Diet.class))).thenReturn(mockDiet);
            when(dietMapper.map(any(Diet.class))).thenReturn(mockDietResponse);

            DietResponseDTO result = dietService.create(mockDietRequest);

            verify(patientService).getById(PATIENT_ID);
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
