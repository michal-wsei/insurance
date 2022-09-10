package pl.wsei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsei.model.Insurance;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
}
