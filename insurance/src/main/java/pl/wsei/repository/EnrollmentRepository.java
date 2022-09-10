package pl.wsei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsei.model.Enrollment;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    List<Enrollment> deleteAllByInsuranceId(Integer id);

    Enrollment findByInsuranceIdInAndUserId(int insurance_id, int user_id);
}
