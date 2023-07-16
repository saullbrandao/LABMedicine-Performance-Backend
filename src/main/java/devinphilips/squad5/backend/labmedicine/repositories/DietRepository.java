package devinphilips.squad5.backend.labmedicine.repositories;

import devinphilips.squad5.backend.labmedicine.models.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietRepository extends JpaRepository<Diet, Integer> {
}
