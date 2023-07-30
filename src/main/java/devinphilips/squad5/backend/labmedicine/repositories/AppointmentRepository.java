package devinphilips.squad5.backend.labmedicine.repositories;

import devinphilips.squad5.backend.labmedicine.models.Appointment;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAllByPatient(Patient patient);
}
