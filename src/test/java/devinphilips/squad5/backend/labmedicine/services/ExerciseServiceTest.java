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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should create new exercise")
        void createExercise() {
            // Arrange
            Patient mockPatient = createMockPatient();
            ExercisePostRequestDTO mockExerciseRequest = createMockExercisePostRequest(ExerciseType.FORCA);
            Exercise mockExercise = createMockExercise(mockPatient);
            ExerciseResponseDTO mockExerciseResponse = createMockExerciseResponse();

            when(patientService.getById(PATIENT_ID)).thenReturn(mockPatient);
            when(exerciseMapper.map(any(ExercisePostRequestDTO.class))).thenReturn(mockExercise);
            when(exerciseRepository.save(any(Exercise.class))).thenReturn(mockExercise);
            when(exerciseMapper.map(any(Exercise.class))).thenReturn(mockExerciseResponse);

            // Act
            ExerciseResponseDTO result = exerciseService.create(mockExerciseRequest);

            // Assert
            verify(patientService).getById(PATIENT_ID);
            verify(exerciseRepository).save(any(Exercise.class));
            Assertions.assertEquals(mockExerciseResponse, result);
            Assertions.assertTrue(result.status());
        }

        @ParameterizedTest
        @EnumSource(ExerciseType.class)
        @DisplayName("Should create new exercise with different exercise types")
        void createExercise_withDifferentTypes(ExerciseType exerciseType) {
            // Arrange
            Patient mockPatient = createMockPatient();
            ExercisePostRequestDTO mockExerciseRequest = createMockExercisePostRequest(exerciseType);
            Exercise mockExercise = createMockExercise(mockPatient);
            ExerciseResponseDTO mockExerciseResponse = createMockExerciseResponse();

            when(patientService.getById(PATIENT_ID)).thenReturn(mockPatient);
            when(exerciseMapper.map(any(ExercisePostRequestDTO.class))).thenReturn(mockExercise);
            when(exerciseRepository.save(any(Exercise.class))).thenReturn(mockExercise);
            when(exerciseMapper.map(any(Exercise.class))).thenReturn(mockExerciseResponse);

            // Act
            ExerciseResponseDTO result = exerciseService.create(mockExerciseRequest);

            // Assert
            verify(patientService).getById(PATIENT_ID);
            verify(exerciseRepository).save(any(Exercise.class));
            Assertions.assertEquals(mockExerciseResponse, result);
            Assertions.assertTrue(result.status());
        }


        private Patient createMockPatient() {
            Patient mockPatient = new Patient();
            mockPatient.setId(PATIENT_ID);
            return mockPatient;
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

        private Exercise createMockExercise(Patient patient) {
            return Exercise.builder()
                    .name("Test Name")
                    .exerciseDate(LocalDate.of(2023, 5, 10))
                    .exerciseTime(LocalTime.of(10, 0))
                    .type(ExerciseType.FORCA)
                    .amountPerWeek(1)
                    .description("Test Description")
                    .patient(patient)
                    .build();
        }

        private ExerciseResponseDTO createMockExerciseResponse() {
            return new ExerciseResponseDTO(
                    1,
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
            // Arrange
            Integer exerciseId = 1;
            ExercisePutRequestDTO exercisePutRequestDTO = new ExercisePutRequestDTO(
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    ExerciseType.RESISTENCIA_MUSCULAR,
                    2,
                    "Updated Description"
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

            // Act
            ExerciseResponseDTO result = exerciseService.update(exerciseId, exercisePutRequestDTO);

            // Assert
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
}
