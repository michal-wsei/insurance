package pl.wsei.services.variant;

import pl.wsei.model.variant.Variant;

public interface VariantService {
    Variant createVariant(Integer insuranceId, Variant variant);

    Variant reOrderVariant(Integer insuranceId, Integer variantId, Integer order);
}
