package pl.wsei.dto.insurance;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.wsei.dto.BaseDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PersonDto extends BaseDto {

    private String name;

    private String email;

    public PersonDto(Integer id, String name, String email) {
        super(id);
        this.name = name;
        this.email = email;
    }
}
