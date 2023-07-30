package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.AppointmentMapper;
import devinphilips.squad5.backend.labmedicine.models.Appointment;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import devinphilips.squad5.backend.labmedicine.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final AppointmentMapper appointmentMapper;
    public AppointmentService( PatientService patientService,
                               AppointmentMapper appointmentMapper,
                               AppointmentRepository appointmentRepository) {
        this.patientService = patientService;
        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
    }

    public List<AppointmentResponseDTO> getAll(Integer id) {
        if (id == null || id== 0) {
            return appointmentMapper.map(appointmentRepository.findAll());
        }
        return appointmentMapper.map(appointmentRepository.findAll()).stream()
                .filter(appointment -> appointment.patientId() == id)
                .collect(Collectors.toList());
        }


    public AppointmentResponseDTO create(AppointmentPostRequestDTO appointmentPostRequestDTO) {

        Patient patient = patientService.findById(appointmentPostRequestDTO.patientId());


        Appointment appointment = appointmentMapper.map(appointmentPostRequestDTO);
        appointment.setPatient(patient);
        appointment.setStatus(true);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.map(savedAppointment);
    }

    public  AppointmentResponseDTO update(
            Integer id,
            AppointmentPutRequestDTO appointmentPutRequestDTO) {
        Appointment existingAppointment = findById(id);

        existingAppointment.setReason(appointmentPutRequestDTO.reason());
        existingAppointment.setAppointmentDate(
                appointmentPutRequestDTO.date().atTime(appointmentPutRequestDTO.time()));
        existingAppointment.setDescription(appointmentPutRequestDTO.description());
        existingAppointment.setMedication(appointmentPutRequestDTO.medication());
        existingAppointment.setDosageAndPrecautions(appointmentPutRequestDTO.dosageAndPrecautions());
        existingAppointment.setStatus(appointmentPutRequestDTO.status());

        Appointment savedAppointment = appointmentRepository.save(existingAppointment);

        return appointmentMapper.map(savedAppointment);
    }

    public AppointmentResponseDTO getById(Integer id) {
        return appointmentMapper.map(appointmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com o ID: " + id)));
    }

    public  Appointment findById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com o ID: " + id));
    }


    public void delete(Integer id) {
        Appointment appointment = findById(id);
        appointmentRepository.delete(appointment);
    }
}
