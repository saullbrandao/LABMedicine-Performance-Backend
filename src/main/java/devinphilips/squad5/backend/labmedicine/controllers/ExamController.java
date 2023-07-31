package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.ExamService;
import devinphilips.squad5.backend.labmedicine.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exames")
public class ExamController {
    private final ExamService examService;
    private final JwtService jwtService;

    public ExamController(ExamService examService, JwtService jwtService) {
        this.examService = examService;
        this.jwtService = jwtService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ExamResponseDTO> getAll(@RequestParam(required = false) String patientName) {
        return examService.getAll(patientName);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ExamResponseDTO get(@PathVariable Integer id) {
        return examService.getById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ExamResponseDTO create(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody @Valid ExamPostRequestDTO examPostRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return examService.create(examPostRequestDTO, userEmail);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ExamResponseDTO update(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Integer id,
                                         @RequestBody @Valid ExamPutRequestDTO examPutRequestDTO) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        return examService.update(id,examPutRequestDTO, userEmail);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable Integer id) {
        var userEmail = jwtService.extractUserName(token.substring(7));
        examService.delete(id, userEmail);
    }}

