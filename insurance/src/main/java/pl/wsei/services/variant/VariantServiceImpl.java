package pl.wsei.services.variant;

import org.springframework.stereotype.Service;
import pl.wsei.controller.exceptions.variant.VariantException;
import pl.wsei.model.Insurance;
import pl.wsei.model.variant.Variant;
import pl.wsei.repository.InsuranceRepository;
import pl.wsei.repository.VariantRepository;

@Service
public class VariantServiceImpl implements VariantService {

    private final InsuranceRepository insuranceRepository;
    private final VariantRepository variantRepository;

    public VariantServiceImpl(InsuranceRepository insuranceRepository, VariantRepository variantRepository) {
        this.insuranceRepository = insuranceRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public Variant createVariant(Integer insuranceId, Variant variant) {
        Insurance insurance = this.insuranceRepository
                .findById(insuranceId)
                .orElseThrow(() -> new VariantException("insurance not found!"));

        variant.setOrderIndex(insurance.getVariant().size());
        variant.setInsurance(insurance);

        return variantRepository.save(variant);
    }

    @Override
    public Variant reOrderVariant(Integer insuranceId, Integer variantId, Integer order) {
        Variant firstvariant = this.variantRepository
                .findById(variantId)
                .orElseThrow(() -> new VariantException("variant not found!"));

        Insurance insurance = this.insuranceRepository
                .findById(insuranceId)
                .orElseThrow(() -> new VariantException("insurance not found!"));

        if (insurance.getVariant().size() <= order || order < 0)
            throw new VariantException("Invalid order index");

        Variant secondvariant = this.variantRepository
                .findByInsurance_IdAndOrderIndex(insuranceId, order)
                .orElseThrow(() -> new VariantException("variant to be swapped with not found!"));

        secondvariant.setOrderIndex(firstvariant.getOrderIndex());
        variantRepository.save(secondvariant);

        firstvariant.setOrderIndex(order);
        return variantRepository.save(firstvariant);
    }

}
