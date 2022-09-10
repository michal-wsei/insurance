package pl.wsei.dto.insurance;

import org.springframework.stereotype.Component;
import pl.wsei.dto.DtoConverter;
import pl.wsei.dto.variant.VariantDto;
import pl.wsei.model.Insurance;

import java.util.stream.Collectors;

@Component
public class InsuranceConverter implements DtoConverter<InsuranceDto, Insurance> {

    @Override
    public InsuranceDto createFrom(Insurance entity) {
        InsuranceDto insuranceDto = new InsuranceDto();
        insuranceDto.setId(entity.getId());
        insuranceDto.setName(entity.getName());
        insuranceDto.setDescription(entity.getDescription());
        PersonDto ownerDTO = new PersonDto(entity.getOwner().getId(), entity.getOwner().getName(), entity.getOwner().getEmail());
        insuranceDto.setClient(entity.getClient()
                .stream()
                .map(enrollment ->
                        new PersonDto(
                                enrollment.getUser().getId(),
                                enrollment.getUser().getName(),
                                enrollment.getUser().getEmail()))
                .collect(Collectors.toSet()));
        insuranceDto.setOwner(ownerDTO);
        insuranceDto.setVariant(entity.getVariant()
                .stream()
                .map(VariantDto::new)
                .collect(Collectors.toList()));
        return insuranceDto;
    }

    @Override
    public Insurance createFrom(InsuranceDto dto) {
        Insurance insurance = new Insurance();
        insurance.setId(dto.getId());
        insurance.setName(dto.getName());
        insurance.setDescription(dto.getDescription());
        return insurance;
    }
}
