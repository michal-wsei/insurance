package pl.wsei.dto.variant;

import org.springframework.stereotype.Component;
import pl.wsei.dto.DtoConverter;
import pl.wsei.model.variant.Variant;

@Component
public class VariantConverter implements DtoConverter<VariantDto, Variant> {
    @Override
    public VariantDto createFrom(Variant entity) {
        return new VariantDto(entity);
    }

    @Override
    public Variant createFrom(VariantDto dto) {
        Variant variant = new Variant();
        variant.setId(dto.getId());
        variant.setOrderIndex(dto.getOrderIndex());
        variant.setContent(dto.getContent());
        variant.setTitle(dto.getTitle());
        return variant;
    }
}
