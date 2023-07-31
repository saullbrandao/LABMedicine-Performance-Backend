package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.appointment.AppointmentResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.AppointmentService;
import devinphilips.squad5.backend.labmedicine.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final JwtService jwtService;

    public AppointmentController(AppointmentService appointmentService, JwtService jwtService) {
        this.appointmentService = appointmentService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AppointmentResponseDTO getById(@PathVariable Integer id) {
        return appointmentService.getById(id);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AppointmentResponseDTO> get(@RequestParam(required = false) Integer id) {
        return appointmentService.getAll(id);
   }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppointmentResponseDTO create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody @Valid AppointmentPostRequestDTO appointmentPostRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return appointmentService.create(appointmentPostRequestDTO, userEmail);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AppointmentResponseDTO update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                         @PathVariable Integer id,
                                         @RequestBody @Valid AppointmentPutRequestDTO appointmentPutRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return appointmentService.update(id,appointmentPutRequestDTO, userEmail);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable Integer id) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        appointmentService.delete(id, userEmail);
    }
}