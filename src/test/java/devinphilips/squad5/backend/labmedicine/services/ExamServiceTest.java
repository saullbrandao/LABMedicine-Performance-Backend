package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.ExamMapper;
import devinphilips.squad5.backend.labmedicine.models.Exam;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.ExamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceTest {
    @Mock
    private ExamRepository examRepository;

    @Mock
    private ExamMapper examMapper;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private ExamService examService;

    private static final Integer PATIENT_ID = 1;

    private Patient mockPatient;
    private Exam mockExam;
    private ExamResponseDTO mockExamResponse;
    private List<Exam> mockExams;
    private List<ExamResponseDTO> mockExamResponses;

    @BeforeEach
    void setUp() {
        mockPatient = createMockPatient();
        mockExam = createMockExam(1);
        mockExamResponse = createMockExamResponse(1);
        mockExams = createMockExams(5);
        mockExamResponses = createMockExamResponses(5);
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {
        @Test
        @DisplayName("Should return list of exam response DTOs when exams exist")
        void whenExamsExist() {
            when(examRepository.findAll()).thenReturn(mockExams);
            when(examMapper.map(mockExams)).thenReturn(mockExamResponses);

            List<ExamResponseDTO> result = examService.getAll(null);

            Assertions.assertEquals(mockExamResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no Exam exist")
        void whenNoExams() {
            when(examRepository.findAll()).thenReturn(Collections.emptyList());

            List<ExamResponseDTO> result = examService.getAll(null);

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }
    @Nested
    @DisplayName("getByPatientName")
    class GetByPatientName {
        @Test
        @DisplayName("Should return list of exam response DTOs for given patient name")
        void whenExamsExistForPatientName() {
            String patientName = "John Doe";
            mockPatient.setName(patientName);

            when(patientService.getByPatientName(patientName)).thenReturn(mockPatient);
            when(examRepository.findAllByPatient(mockPatient)).thenReturn(mockExams);
            when(examMapper.map(mockExams)).thenReturn(mockExamResponses);

            List<ExamResponseDTO> result = examService.getAll(patientName);

            Assertions.assertEquals(mockExamResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no exams found for given patient name")
        void whenNoExamsForPatientName() {
            String patientName = "John Doe";
            mockPatient.setName(patientName);

            when(patientService.getByPatientName(patientName)).thenReturn(mockPatient);
            when(examRepository.findAllByPatient(mockPatient)).thenReturn(Collections.emptyList());

            List<ExamResponseDTO> result = examService.getAll(patientName);

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getById")
    class GetById {
        @Test
        @DisplayName("Should return exam response DTO for given id")
        void whenExamExists() {
            when(examRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockExam));
            when(examMapper.map(mockExam)).thenReturn(mockExamResponse);

            ExamResponseDTO result = examMapper.map(examService.findById(anyInt()));

            Assertions.assertEquals(mockExamResponse, result);
        }

        @Test
        @DisplayName("Should throw error when no exam found for given id")
        void whenExamDoesNotExist() {
            when(examRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> examService.findById(anyInt()));
        }
    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should create new exam")
        void createExam() {
            ExamPostRequestDTO mockExamRequest = createMockExamPostRequest();
            Exam mockExam = createMockExam(1);
            ExamResponseDTO mockExamResponse = createMockExamResponse(1);

            when(patientService.findById(PATIENT_ID)).thenReturn(mockPatient);
            when(examMapper.map(any(ExamPostRequestDTO.class))).thenReturn(mockExam);
            when(examRepository.save(any(Exam.class))).thenReturn(mockExam);
            when(examMapper.map(any(Exam.class))).thenReturn(mockExamResponse);

            ExamResponseDTO result = examService.create(mockExamRequest);

            verify(patientService).findById(PATIENT_ID);
            verify(examRepository).save(any(Exam.class));
            Assertions.assertEquals(mockExamResponse, result);
            Assertions.assertTrue(result.status());
        }


        private ExamPostRequestDTO createMockExamPostRequest() {
            return new ExamPostRequestDTO(
                    "Test Exam",
                    LocalDate.of(2023, 5, 10),
                    LocalTime.of(10, 0),
                    "Test Type Exam",
                    "Test Exam Laboratory",
                    "www.examlab.com",
                    "Test Results Exams",
                    PATIENT_ID
            );
        }
    }

    @Nested
    @DisplayName(("update"))
    class Update {
        @Test
        @DisplayName("Should throw exception when exam not found")
        void notFound() {
            ExamPutRequestDTO mockRequest = mock(ExamPutRequestDTO.class);

            when(examRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> examService.update(anyInt(), mockRequest));
        }


        @Test
        @DisplayName("Should update exam when the exams exists")
        void found() {
            Integer examId = 1;
            ExamPutRequestDTO examPutRequestDTO = new ExamPutRequestDTO(
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    "Updated Type Exam",
                    "Updated Laboratory Exam",
                    "www.examlab.com",
                    "Updated Results Exam",
                    true
            );

            Exam existingExam = Exam.builder()
                    .id(examId)
                    .name("Old Name")
                    .examDate(LocalDateTime.of(2023, 8, 10,10,0))
                    .type("Old Type Exam")
                    .laboratory("Old Laboratory Exam")
                    .url("www.examlab.com")
                    .results("Old Results Exam")
                    .build();

            Exam savedExam = Exam.builder()
                    .id(examId)
                    .name("Updated Name")
                    .examDate(LocalDateTime.of(2023, 8, 15,11,30))
                    .type("Updated Type Exam")
                    .laboratory("Updated Laboratory Exam")
                    .url("www.examlab.com")
                    .results("Updated Results Exam")
                    .build();

            ExamResponseDTO expectedResponse = new ExamResponseDTO(
                    examId,
                    "Updated Name",
                    LocalDate.of(2023, 8, 15),
                    LocalTime.of(11, 30),
                    "Updated Type Exam",
                    "Updated Laboratory Exam",
                    "www.examlab.com",
                    "Updated Results Exam",
                    true,
                    PATIENT_ID
            );

            when(examRepository.findById(examId)).thenReturn(Optional.of(existingExam));
            when(examRepository.save(any(Exam.class))).thenReturn(savedExam);
            when(examMapper.map(savedExam)).thenReturn(expectedResponse);

            ExamResponseDTO result = examService.update(examId, examPutRequestDTO);

            verify(examRepository).findById(examId);
            verify(examRepository).save(any(Exam.class));
            verify(examMapper).map(savedExam);

            Assertions.assertEquals(examPutRequestDTO.name(), savedExam.getName());
            Assertions.assertEquals(examPutRequestDTO.type(), savedExam.getType());
            Assertions.assertEquals(examPutRequestDTO.results(), savedExam.getResults());
            Assertions.assertEquals(examPutRequestDTO.laboratory(), savedExam.getLaboratory());


            Assertions.assertEquals(expectedResponse, result);
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("Should throw exception when exam not found")
        void notFound() {
            when(examRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> examService.delete(anyInt()));
        }

        @Test
        @DisplayName("Should delete exam when the exam exists")
        void found() {
            Exam mockExam = mock(Exam.class);
            when(examRepository.findById(anyInt())).thenReturn(Optional.of(mockExam));

            examService.delete(anyInt());

            verify(examRepository).delete(mockExam);
        }
    }

    private Patient createMockPatient() {
        Patient mockPatient = new Patient();
        mockPatient.setId(PATIENT_ID);
        return mockPatient;
    }

    private Exam createMockExam(Integer id) {
        return Exam.builder()
                .id(id)
                .name("Exam Name")
                .examDate(LocalDateTime.of(2023,5,10,10,0))
                .type("Exam Type")
                .laboratory("Exam Laboratory")
                .url("www.examlab.com")
                .results("Exam Results")
                .status(true)
                .patient(createMockPatient())
                .build();
    }

    private ExamResponseDTO createMockExamResponse(Integer id) {
        return new ExamResponseDTO(
                id,
                "Exam Name",
                LocalDate.of(2023, 5, 10),
                LocalTime.of(10, 0),
                "Exam Type",
                "Exam Laboratory",
                "www.examlab.com",
                "Exam Results",
                true,
                PATIENT_ID
        );
    }

    private List<Exam> createMockExams(int count) {
        List<Exam> exams = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            exams.add(createMockExam(i));
        }
        return exams;
    }

    private List<ExamResponseDTO> createMockExamResponses(int count) {
        List<ExamResponseDTO> examResponses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            examResponses.add(createMockExamResponse(i));
        }
        return examResponses;
    }

}