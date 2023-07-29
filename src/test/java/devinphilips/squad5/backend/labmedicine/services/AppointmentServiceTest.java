package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.AppointmentMapper;
import devinphilips.squad5.backend.labmedicine.models.Appointment;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentService appointmentService;

    private static final Integer PATIENT_ID = 1;

    private Patient mockPatient;
    private Appointment mockAppointment;
    private AppointmentResponseDTO mockAppointmentResponse;
    private List<Appointment> mockAppointments;
    private List<AppointmentResponseDTO> mockAppointmentResponses;

    @BeforeEach
    void setUp() {
        mockPatient = createMockPatient();
        mockAppointment = createMockAppointment(1);
        mockAppointmentResponse = createMockAppointmentResponse(1);
        mockAppointments = createMockAppointments(5);
        mockAppointmentResponses = createMockAppointmentResponses(5);
    }


    @Nested
    @DisplayName("getAll")
    class GetAll {
        @Test
        @DisplayName("Should return list of appointment response DTOs when appointments exist")
        void whenAppointmentsExist() {
            when(appointmentRepository.findAll()).thenReturn(mockAppointments);
            when(appointmentMapper.map(mockAppointments)).thenReturn(mockAppointmentResponses);

            List<AppointmentResponseDTO> result = appointmentService.getAll(0);

            Assertions.assertEquals(mockAppointmentResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no appointments exist")
        void whenNoAppointments() {
            when(appointmentRepository.findAll()).thenReturn(Collections.emptyList());

            List<AppointmentResponseDTO> result = appointmentService.getAll(123456);

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getByPatientId")
    class GetByPatientName {
        @Test
        @DisplayName("Should return list of appointment response DTOs for given patient id")
        void whenAppointmentsExistForPatientName() {
            mockPatient.setId(PATIENT_ID);


            when(appointmentRepository.findAll()).thenReturn(mockAppointments);
            when(appointmentMapper.map(mockAppointments)).thenReturn(mockAppointmentResponses);

            List<AppointmentResponseDTO> result = appointmentService.getAll(PATIENT_ID);

            Assertions.assertEquals(mockAppointmentResponses, result);
        }

        @Test
        @DisplayName("Should return empty list when no appointments found for given patient id")
        void whenNoAppointmentsForPatientId() {
            mockPatient.setId(PATIENT_ID);

            when(appointmentRepository.findAll()).thenReturn(Collections.emptyList());

            List<AppointmentResponseDTO> result = appointmentService.getAll(PATIENT_ID);

            Assertions.assertEquals(Collections.emptyList(), result);
        }
    }

    @Nested
    @DisplayName("getById")
    class GetById {
        @Test
        @DisplayName("Should return appointment response DTO for given id")
        void whenAppointmentExists() {

            when(appointmentRepository.findById(anyInt())).thenReturn(Optional.ofNullable(mockAppointment));
            when(appointmentMapper.map(mockAppointment)).thenReturn(mockAppointmentResponse);

            AppointmentResponseDTO result = appointmentMapper.map(appointmentService.findById(anyInt()));

            Assertions.assertEquals(mockAppointmentResponse, result);
        }


        @Test
        @DisplayName("Should throw error when no appointment found for given id")
        void whenAppointmentDoesNotExist() {
            when(appointmentRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> appointmentService.findById(anyInt()));
        }

    }

    @Nested
    @DisplayName("create")
    class Create {
        @Test
        @DisplayName("Should create new appointment")
        void createAppointment() {
            AppointmentPostRequestDTO mockAppointmentRequest = createMockAppointmentPostRequest(PATIENT_ID);
            Appointment mockAppointment = createMockAppointment(1);
            AppointmentResponseDTO mockAppointmentResponse = createMockAppointmentResponse(1);

            when(patientService.findById(PATIENT_ID)).thenReturn(mockPatient);
            when(appointmentMapper.map(any(AppointmentPostRequestDTO.class))).thenReturn(mockAppointment);
            when(appointmentRepository.save(any(Appointment.class))).thenReturn(mockAppointment);
            when(appointmentMapper.map(any(Appointment.class))).thenReturn(mockAppointmentResponse);

            AppointmentResponseDTO result = appointmentService.create(mockAppointmentRequest);

            verify(patientService).findById(PATIENT_ID);
            verify(appointmentRepository).save(any(Appointment.class));
            Assertions.assertEquals(mockAppointmentResponse, result);
            Assertions.assertTrue(result.status());
        }


        private AppointmentPostRequestDTO createMockAppointmentPostRequest(Integer id) {
            return new AppointmentPostRequestDTO(
                    "Test Reason",
                    LocalDate.of(2023, 5, 10),
                    LocalTime.of(10, 0),
                    "Test Description",
                    "Test Medication",
                    "Test Dosage and Precautions",
                    id
            );
        }
    }




    @Nested
    @DisplayName(("update"))
    class Update {
        @Test
        @DisplayName("Should throw exception when appointment not found")
        void notFound() {
            AppointmentPutRequestDTO mockRequest = mock(AppointmentPutRequestDTO.class);

            when(appointmentRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> appointmentService.update(anyInt(), mockRequest));
        }


        @Test
        @DisplayName("Should update appointment when the appointment exists")
        void found() {
                    AppointmentPutRequestDTO appointmentPutRequestDTO = new AppointmentPutRequestDTO(
                            "Update Reason",
                            (LocalDate.of(2023, 8, 10)),
                            (LocalTime.of(10, 0)),
                            "Update Description",
                            "Update Medication",
                            "Update dosage and Precautions",
                            true
            );

            Appointment existingAppointment = Appointment.builder()
                    .id(1)
                    .reason("Old Reason")
                    .appointmentDate(LocalDateTime.of(2023, 8, 10,10,0))
                    .dosageAndPrecautions("Old dosage and Precautions")
                    .description("Old Description")
                    .patient(createMockPatient())
                    .build();

            Appointment savedAppointment = Appointment.builder()
                    .id(1)
                    .reason("Update Reason")
                    .appointmentDate(LocalDateTime.of(2023, 8, 10,10,0))
                    .dosageAndPrecautions("Update dosage and Precautions")
                    .medication("Update Medication")
                    .description("Update Description")
                    .patient(createMockPatient())
                    .build();

            AppointmentResponseDTO expectedResponse = new AppointmentResponseDTO(
                1,
                    "Update Reason",
                    (LocalDate.of(2023, 8, 10)),
                    (LocalTime.of(10, 0)),
                    "Update Description",
                    "Update Medication",
                    "Update dosage and Precautions",
                    true,
                    PATIENT_ID

            );

            when(appointmentRepository.findById(PATIENT_ID)).thenReturn(Optional.of(existingAppointment));
            when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);
            when(appointmentMapper.map(savedAppointment)).thenReturn(expectedResponse);

            AppointmentResponseDTO result = appointmentService.update(PATIENT_ID, appointmentPutRequestDTO);

            verify(appointmentRepository).findById(PATIENT_ID);
            verify(appointmentRepository).save(any(Appointment.class));
            verify(appointmentMapper).map(savedAppointment);

            Assertions.assertEquals(appointmentPutRequestDTO.reason(), savedAppointment.getReason());
            Assertions.assertEquals(appointmentPutRequestDTO.description(), savedAppointment.getDescription());
            Assertions.assertEquals(appointmentPutRequestDTO.dosageAndPrecautions(), savedAppointment.getDosageAndPrecautions());
            Assertions.assertEquals(appointmentPutRequestDTO.medication(), savedAppointment.getMedication());
            Assertions.assertEquals(expectedResponse, result);
        }
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("Should throw exception when appointment not found")
        void notFound() {
            when(appointmentRepository.findById(anyInt())).thenReturn(Optional.empty());

            Assertions.assertThrows(EntityNotFoundException.class, () -> appointmentService.delete(anyInt()));
        }

        @Test
        @DisplayName("Should delete appointment when the appointment exists")
        void found() {
            Appointment mockAppointment = mock(Appointment.class);
            when(appointmentRepository.findById(anyInt())).thenReturn(Optional.of(mockAppointment));

            appointmentService.delete(anyInt());

            verify(appointmentRepository).delete(mockAppointment);
        }
    }

    private Patient createMockPatient() {
            Patient mockPatient = new Patient();
            mockPatient.setId(PATIENT_ID);
            return mockPatient;
    }

        private Appointment createMockAppointment(Integer id) {
            return Appointment.builder()
                    .id(id)
                    .reason("Reason Name")
                    .appointmentDate(LocalDateTime.of(2023, 5, 10, 15, 30))
                    .description("Test Description")
                    .medication("Test Medication")
                    .dosageAndPrecautions("Teste Dosage and Precautions")
                    .patient(createMockPatient())
                    .build();
        }

    private AppointmentResponseDTO createMockAppointmentResponse(Integer id) {
        return new AppointmentResponseDTO(
                id,
                "Test Reason",
                LocalDate.of(2023, 5, 10),
                LocalTime.of(10, 0),
                "Test Description",
                "Test Medication",
                "Test Dosage and Medications",
                true,
                PATIENT_ID

        );
    }



    private List<Appointment> createMockAppointments(int count) {
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            appointments.add(createMockAppointment(i));
        }
        return appointments;
    }

    private List<AppointmentResponseDTO> createMockAppointmentResponses(int count) {
        List<AppointmentResponseDTO> appointmentResponses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            appointmentResponses.add(createMockAppointmentResponse(i));
        }
        return appointmentResponses;
    }
}


