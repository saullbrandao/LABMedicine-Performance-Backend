package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExercisePutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exercise.ExerciseResponseDTO;
import devinphilips.squad5.backend.labmedicine.enums.ExerciseType;
import devinphilips.squad5.backend.labmedicine.mappers.ExerciseMapper;
import devinphilips.squad5.backend.labmedicine.models.Exercise;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.ExerciseRepository;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {
    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private ExerciseService exerciseService;

    private static final Integer PATIENT_ID = 1;

    private Patient mockPatient;
    private Exercise mockExercise;
    private ExerciseResponseDTO mockExerciseResponse;
    private List<Exercise> mockExercises;
    private List<ExerciseResponseDTO> mockExerciseResponses;

    @BeforeEach
    void setUp() {
        mockPatient = createMockPatient();
        mockExercise = createMockExercise(1);
        mockExerciseResponse = createMockExerciseResponse(1);
        mockExercises = createMockExercises(5);
        mockExerciseResponses = createMockExerciseResponses(5);
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {
        @Test
        @DisplayName("Should return list of exercise response DTOs when exercises exist")
        void whenExercisesExist() {
            when(exerciseRepository.findAll()).thenReturn(mockExercises);
            when(exerciseMapper.map(mockExercises)).thenReturn(mockExerciseResponses);

            List<ExerciseResponseDTO> result = exerciseService.getAll();

            Assertions.assertEquals(mockExerciseResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no exercises exist")
        void whenNoExercises() {
            when(exerciseRepository.findAll()).thenReturn(Collections.emptyList());

            List<ExerciseResponseDTO> result = exerciseService.getAll();

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getByPatientName")
    class GetByPatientName {
        @Test
        @DisplayName("Should return list of exercise response DTOs for given patient name")
        void whenExercisesExistForPatientName() {
            String patientName = "John Doe";
            mockPatient.setName(patientName);

            when(patientService.getByPatientName(patientName)).thenReturn(mockPatient);
            when(exerciseRepository.findAllByPatient(mockPatient)).thenReturn(mockExercises);
            when(exerciseMapper.map(mockExercises)).thenReturn(mockExerciseResponses);

            List<ExerciseResponseDTO> result = exerciseService.getByPatientName(patientName);

            Assertions.assertEquals(mockExerciseResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no exercises found for given patient name")
        void whenNoExercisesForPatientName() {
            String patientName = "John Doe";
            mockPatient.setName(patientName);

            when(patientService.getByPatientName(patientName)).thenReturn(mockPatient);
            when(exerciseRepository.findAllByPatient(mockPatient)).thenReturn(Collections.emptyList());

            List<ExerciseResponseDTO> result = exerciseService.getByPatientName(patientName);

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getById")
    class GetById {
        @Test
        @DisplayName("Should return exercise response DTO for given id")
        void whenExerciseExists() {
            when(exerciseRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockExercise));
            when(exerciseMapper.map(mockExercise)).thenReturn(mockExerciseResponse);

            ExerciseResponseDTO result = exerciseService.getById(anyInt());

            Assertions.assertEquals(mockExerciseResponse, result);
        }

        @Test
        @DisplayName("Should throw error when no exercise found for given id")
        void whenExerciseDoesNotExist() {
            when(exerciseRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> exerciseService.getById(anyInt()));
        }
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should create new exercise")
        void createExercise() {
            Patient mockPatient = createMockPatient();
            ExercisePostRequestDTO mockExerciseRequest = createMockExercisePostRequest(ExerciseType.FORCA);
            Exercise mockExercise = createMockExercise(1);
            ExerciseResponseDTO mockExerciseResponse = createMockExerciseResponse(1);

            when(patientService.findById(PATIENT_ID)).thenReturn(mockPatient);
            when(exerciseMapper.map(any(ExercisePostRequestDTO.class))).thenReturn(mockExercise);
            when(exerciseRepository.save(any(Exercise.class))).thenReturn(mockExercise);
            when(exerciseMapper.map(any(Exercise.class))).thenReturn(mockExerciseResponse);

            ExerciseResponseDTO result = exerciseService.create(mockExerciseRequest);

            verify(patientService).findById(PATIENT_ID);
            verify(exerciseRepository).save(any(Exercise.class));
            Assertions.assertEquals(mockExerciseResponse, result);
            Assertions.assertTrue(result.status());
        }

        @ParameterizedTest
        @EnumSource(ExerciseType.class)
        @DisplayName("Should create new exercise with different exercise types")
        void createExercise_withDifferentTypes(ExerciseType exerciseType) {
            Patient mockPatient = createMockPatient();
            ExercisePostRequestDTO mockExerciseRequest = createMockExercisePostRequest(exerciseType);
            Exercise mockExercise = createMockExercise(1);
            ExerciseResponseDTO mockExerciseResponse = createMockExerciseResponse(1);

            when(patientService.findById(PATIENT_ID)).thenReturn(mockPatient);
            when(exerciseMapper.map(any(ExercisePostRequestDTO.class))).thenReturn(mockExercise);
            when(exerciseRepository.save(any(Exercise.class))).thenReturn(mockExercise);
            when(exerciseMapper.map(any(Exercise.class))).thenReturn(mockExerciseResponse);

            ExerciseResponseDTO result = exerciseService.create(mockExerciseRequest);

            verify(patientService).findById(PATIENT_ID);
            verify(exerciseRepository).save(any(Exercise.class));
            Assertions.assertEquals(mockExerciseResponse, result);
            Assertions.assertTrue(result.status());
        }


        private ExercisePostRequestDTO createMockExercisePostRequest(ExerciseType exerciseType) {
            return new ExercisePostRequestDTO(
                    "Test Name",
                    LocalDate.of(2023, 5, 10),
                    LocalTime.of(10, 0),
                    exerciseType,
                    1,
                    "Test Description",
                    PATIENT_ID
            );
        }
    }

    @Nested
    @DisplayName(("update"))
    class Update {
        @Test
        @DisplayName("Should throw exception when exercise not found")
        void notFound() {
            ExercisePutRequestDTO mockRequest = mock(ExercisePutRequestDTO.class);

            when(exerciseRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> exerciseService.update(anyInt(), mockRequest));
        }


        @Test
        @DisplayName("Should update exercise when the exercise exists")
        void found() {
            Integer exerciseId = 1;
            ExercisePutRequestDTO exercisePutRequestDTO = new ExercisePutRequestDTO(
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    ExerciseType.RESISTENCIA_MUSCULAR,
                    2,
                    "Updated Description",
                    false
            );

            Exercise existingExercise = Exercise.builder()
                    .id(exerciseId)
                    .name("Old Name")
                    .exerciseDate(LocalDate.of(2023, 8, 10))
                    .exerciseTime(LocalTime.of(10, 0))
                    .type(ExerciseType.FORCA)
                    .amountPerWeek(1)
                    .description("Old Description")
                    .build();

            Exercise savedExercise = Exercise.builder()
                    .id(exerciseId)
                    .name("Updated Name")
                    .exerciseDate(LocalDate.of(2023, 8, 15))
                    .exerciseTime(LocalTime.of(11, 30))
                    .type(ExerciseType.RESISTENCIA_MUSCULAR)
                    .amountPerWeek(2)
                    .description("Updated Description")
                    .build();

            ExerciseResponseDTO expectedResponse = new ExerciseResponseDTO(
                    exerciseId,
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    ExerciseType.RESISTENCIA_MUSCULAR,
                    2,
                    "Updated Description",
                    true,
                    null
            );

            when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(existingExercise));
            when(exerciseRepository.save(any(Exercise.class))).thenReturn(savedExercise);
            when(exerciseMapper.map(savedExercise)).thenReturn(expectedResponse);

            ExerciseResponseDTO result = exerciseService.update(exerciseId, exercisePutRequestDTO);

            verify(exerciseRepository).findById(exerciseId);
            verify(exerciseRepository).save(any(Exercise.class));
            verify(exerciseMapper).map(savedExercise);

            Assertions.assertEquals(exercisePutRequestDTO.name(), savedExercise.getName());
            Assertions.assertEquals(exercisePutRequestDTO.date(), savedExercise.getExerciseDate());
            Assertions.assertEquals(exercisePutRequestDTO.time(), savedExercise.getExerciseTime());
            Assertions.assertEquals(exercisePutRequestDTO.type(), savedExercise.getType());
            Assertions.assertEquals(exercisePutRequestDTO.amountPerWeek(), savedExercise.getAmountPerWeek());
            Assertions.assertEquals(exercisePutRequestDTO.description(), savedExercise.getDescription());
            Assertions.assertEquals(expectedResponse, result);
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("Should throw exception when exercise not found")
        void notFound() {
            when(exerciseRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> exerciseService.delete(anyInt()));
        }

        @Test
        @DisplayName("Should delete exercise when the exercise exists")
        void found() {
            Exercise mockExercise = mock(Exercise.class);
            when(exerciseRepository.findById(anyInt())).thenReturn(Optional.of(mockExercise));

            exerciseService.delete(anyInt());

            verify(exerciseRepository).delete(mockExercise);
        }
    }

    private Patient createMockPatient() {
        Patient mockPatient = new Patient();
        mockPatient.setId(PATIENT_ID);
        return mockPatient;
    }

    private Exercise createMockExercise(Integer id) {
        return Exercise.builder()
                .id(id)
                .name("Test Name")
                .exerciseDate(LocalDate.of(2023, 5, 10))
                .exerciseTime(LocalTime.of(10, 0))
                .type(ExerciseType.FORCA)
                .amountPerWeek(1)
                .description("Test Description")
                .patient(createMockPatient())
                .build();
    }

    private ExerciseResponseDTO createMockExerciseResponse(Integer id) {
        return new ExerciseResponseDTO(
                id,
                "Test Name",
                LocalDate.of(2023, 5, 10),
                LocalTime.of(10, 0),
                ExerciseType.FORCA,
                1,
                "Test Description",
                true,
                PATIENT_ID
        );
    }

    private List<Exercise> createMockExercises(int count) {
        List<Exercise> exercises = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            exercises.add(createMockExercise(i));
        }
        return exercises;
    }

    private List<ExerciseResponseDTO> createMockExerciseResponses(int count) {
        List<ExerciseResponseDTO> exerciseResponses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            exerciseResponses.add(createMockExerciseResponse(i));
        }
        return exerciseResponses;
    }
}
