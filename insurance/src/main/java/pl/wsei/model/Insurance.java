package pl.wsei.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.wsei.model.authorization.User;
import pl.wsei.model.generic.BaseEntity;
import pl.wsei.model.variant.Variant;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Builder
@Entity
@Table(name = "insurance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Insurance extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(min = 4, max = 32)
    private String name;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Date modifiedDate;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "insurance")
    private Set<Enrollment> client = new HashSet<>();

    @OneToMany(mappedBy = "insurance")
    @OrderBy("orderIndex asc")
    private List<Variant> variant = new ArrayList<>();
}
