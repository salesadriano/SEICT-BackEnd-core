package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "responsible_area", schema = "system")
@AuditTable(schema = "system_audit", value = "responsible_area_audit")
public class ResponsibleArea extends EntityBase implements Serializable {

    private static final long serialVersionUID = -4714829114589071076L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String nameArea;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "{field.notnull}")
    private User manager;

    private String observation;

    @Column(columnDefinition = "boolean default false")
    private Boolean humanResources;

}