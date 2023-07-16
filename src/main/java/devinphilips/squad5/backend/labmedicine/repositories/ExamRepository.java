package devinphilips.squad5.backend.labmedicine.repositories;

import devinphilips.squad5.backend.labmedicine.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
}
