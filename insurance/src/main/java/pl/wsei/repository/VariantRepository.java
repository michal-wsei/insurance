package pl.wsei.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsei.model.variant.Variant;

import java.util.Optional;

public interface VariantRepository extends JpaRepository<Variant, Integer> {
    Optional<Variant> findByInsurance_IdAndOrderIndex(Integer insuranceId, Integer orderIndex);
}
