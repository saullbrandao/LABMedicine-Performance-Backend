package devinphilips.squad5.backend.labmedicine.controllers;


import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPostRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamPutRequestDTO;
import devinphilips.squad5.backend.labmedicine.dtos.exam.ExamResponseDTO;
import devinphilips.squad5.backend.labmedicine.services.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/exames")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ExamResponseDTO> get(@RequestParam(required = false) String patientName) {
        return examService.getAll(patientName);

    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ExamResponseDTO create(@RequestBody @Valid ExamPostRequestDTO examPostRequestDTO) {
        return examService.create(examPostRequestDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ExamResponseDTO update(@PathVariable Integer id,
                                         @RequestBody @Valid ExamPutRequestDTO examPutRequestDTO) {
        return examService.update(id,examPutRequestDTO);
    }
    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void delete(@PathVariable Integer id) {
        examService.delete(id);
    }}

