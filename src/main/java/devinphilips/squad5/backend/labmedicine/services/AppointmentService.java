package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.mappers.AppointmentMapper;
import devinphilips.squad5.backend.labmedicine.models.Appointment;
import devinphilips.squad5.backend.labmedicine.repositories.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final UsersService usersService;
    private final AppointmentMapper appointmentMapper;
    private final LogService logService;

    public AppointmentService(PatientService patientService,
                              AppointmentMapper appointmentMapper,
                              AppointmentRepository appointmentRepository, UsersService usersService, LogService logService) {
        this.patientService = patientService;
        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
        this.usersService = usersService;
        this.logService = logService;
    }

    public List<AppointmentResponseDTO> getAll(Integer id) {
        if (id == null || id== 0) {
            return appointmentMapper.map(appointmentRepository.findAll());
        }
        return appointmentMapper.map(appointmentRepository.findAll()).stream()
                .filter(appointment -> appointment.patientId() == id)
                .collect(Collectors.toList());
    }

    public AppointmentResponseDTO create(AppointmentPostRequestDTO appointmentPostRequestDTO, String userEmail) {
        var patient = patientService.findById(appointmentPostRequestDTO.patientId());
        var user = usersService.getByEmail(userEmail);

        var appointment = appointmentMapper.map(appointmentPostRequestDTO);
        appointment.setPatient(patient);
        appointment.setStatus(true);
        var savedAppointment = appointmentRepository.save(appointment);

        logService.registerCreate(user, patient, "uma consulta");

        return appointmentMapper.map(savedAppointment);
    }

    public  AppointmentResponseDTO update(
            Integer id,
            AppointmentPutRequestDTO appointmentPutRequestDTO,
            String userEmail) {
        var existingAppointment = findById(id);
        var user = usersService.getByEmail(userEmail);
        var patient = patientService.findById(existingAppointment.getPatient().getId());

        existingAppointment.setReason(appointmentPutRequestDTO.reason());
        existingAppointment.setAppointmentDate(appointmentPutRequestDTO.date().atTime(appointmentPutRequestDTO.time()));
        existingAppointment.setDescription(appointmentPutRequestDTO.description());
        existingAppointment.setMedication(appointmentPutRequestDTO.medication());
        existingAppointment.setDosageAndPrecautions(appointmentPutRequestDTO.dosageAndPrecautions());
        existingAppointment.setStatus(appointmentPutRequestDTO.status());

        var savedAppointment = appointmentRepository.save(existingAppointment);

        logService.registerUpdate(user, patient, savedAppointment.getId(), "uma consulta");

        return appointmentMapper.map(savedAppointment);
    }

    public AppointmentResponseDTO getById(Integer id) {
        return appointmentMapper.map(appointmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com o ID: " + id)));
    }

    public  Appointment findById(Integer id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada com o ID: " + id));
    }


    public void delete(Integer id, String userEmail) {
        var appointment = findById(id);
        var user = usersService.getByEmail(userEmail);
        var patient = patientService.findById(appointment.getPatient().getId());

        appointmentRepository.delete(appointment);

        logService.registerDelete(user, patient, appointment.getId(), "uma consulta");
    }
}
