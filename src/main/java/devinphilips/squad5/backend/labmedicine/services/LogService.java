package devinphilips.squad5.backend.labmedicine.services;

import devinphilips.squad5.backend.labmedicine.dtos.user.UserResponseDTO;
import devinphilips.squad5.backend.labmedicine.models.Log;
import devinphilips.squad5.backend.labmedicine.models.Patient;
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

    public void registerCreate(UserResponseDTO user, Patient patient, String entity) {
        var log = new Log(
                String.format("O %s %s cadastrou %s para o(a) paciente %s (%s)", user.type(), user.name(), entity, patient.getName(), patient.getCpf())
        );

        save(log);
    }

    public void registerUpdate(UserResponseDTO user, Patient patient, Integer entityId, String entity) {
        var log = new Log(
                String.format("O %s %s atualizou %s (id: %s) do(a) paciente %s (%s)", user.type(), user.name(), entity, entityId, patient.getName(), patient.getCpf())
        );

        save(log);
    }

    public void registerDelete(UserResponseDTO user, Patient patient, Integer entityId, String entity) {
        var log = new Log(
                String.format("O %s %s excluiu %s (id: %s) do(a) paciente %s (%s)", user.type(), user.name(), entity, entityId, patient.getName(), patient.getCpf())
        );

        save(log);
    }
}
