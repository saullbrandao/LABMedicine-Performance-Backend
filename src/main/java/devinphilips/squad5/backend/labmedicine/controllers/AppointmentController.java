package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/consultas")
public class AppointmentController {
    private final AppointmentService appointmentService;


    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AppointmentResponseDTO> get(@RequestParam(required = false) Integer id) {
        return appointmentService.getAll(id);
   }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppointmentResponseDTO create(@RequestBody @Valid AppointmentPostRequestDTO appointmentPostRequestDTO) {
        return appointmentService.create(appointmentPostRequestDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AppointmentResponseDTO update(@PathVariable Integer id,
                                         @RequestBody @Valid AppointmentPutRequestDTO appointmentPutRequestDTO) {
        return appointmentService.update(id,appointmentPutRequestDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        appointmentService.delete(id);
    }
}