package devinphilips.squad5.backend.labmedicine.repositories;


import devinphilips.squad5.backend.labmedicine.models.Medication;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {

    List<Medication> findAllByPatient(Patient patient);
}
