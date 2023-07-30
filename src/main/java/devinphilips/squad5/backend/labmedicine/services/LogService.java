package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.models.Log;
import devinphilips.squad5.backend.labmedicine.repositories.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;

    public List<Log> getAll() {
        return logRepository.findAll();
    }

    public Log save(Log log) {
        return logRepository.save(log);
    }
}
