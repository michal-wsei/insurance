package pl.wsei.dto.variant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.wsei.dto.BaseDto;
import pl.wsei.model.variant.Variant;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class VariantDto extends BaseDto {
    private Integer orderIndex;
    private String title;
    private String content;

    public VariantDto(Variant variant) {
        super(variant.getId());
        this.orderIndex = variant.getOrderIndex();
        this.title = variant.getTitle();
        this.content = variant.getContent();

    }
}
