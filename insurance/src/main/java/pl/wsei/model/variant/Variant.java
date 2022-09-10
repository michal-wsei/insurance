package pl.wsei.model.variant;

import lombok.*;
import pl.wsei.model.Insurance;
import pl.wsei.model.generic.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Variant extends BaseEntity {

    @Column(name = "order_index")
    @NotNull
    private Integer orderIndex;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "content")
    private String content;


}
