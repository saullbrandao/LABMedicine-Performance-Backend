package devinphilips.squad5.backend.labmedicine.controllers;

import devinphilips.squad5.backend.labmedicine.dtos.StatisticDTO;
import devinphilips.squad5.backend.labmedicine.services.StatisctService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
public class StatiscController {
    private final StatisctService statisctService;

    public StatiscController(StatisctService statisctService) {
        this.statisctService = statisctService;
    }

    @GetMapping
    public StatisticDTO get(){
        return statisctService.getStats();
    }
}
