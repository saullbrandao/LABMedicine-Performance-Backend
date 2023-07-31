package devinphilips.squad5.backend.labmedicine.repositories;

import devinphilips.squad5.backend.labmedicine.models.Exam;
import devinphilips.squad5.backend.labmedicine.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
    List<Exam> findAllByPatient(Patient patient);
}
