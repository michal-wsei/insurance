package pl.wsei.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base entity dto.
 * Used to map all entities that extend BaseEntity class
 *
 * @see pl.wsei.model.generic.BaseEntity
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto extends Dto {
    private Integer id;
}
