package pl.wsei.dto.insurance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.wsei.dto.BaseDto;
import pl.wsei.dto.variant.VariantDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class InsuranceDto extends BaseDto {

    @NotNull
    @Size(min = 4, max = 32)
    private String name;

    @NotNull
    private String description;

    private PersonDto owner;

    private Set<PersonDto> client = new HashSet<>();
    private List<VariantDto> variant = new ArrayList<>();
}
