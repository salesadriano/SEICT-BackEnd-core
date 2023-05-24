package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.accesscontrol.model.enumerated.EnumSystemType;
import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Audited
@AuditTable(schema = "system_audit", value = "systems_audit")
@Table(name = "system", schema = "system")
public class Systems extends EntityBase implements Serializable {
    private static final long serialVersionUID = 5662656226195143643L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty(message = "{field.notEmpty}")
    @NotNull(message = "{field.notnull}")
    @Column(unique = true)
    private String name;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String initials;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String database;

    @Enumerated(EnumType.ORDINAL)
    @NotNull(message = "{field.notnull}")
    private EnumSystemType enumSystemType;

    @ManyToOne
    @JoinColumn(name = "responsible_area_id")
    private ResponsibleArea responsibleArea;

    private String host;

    private String description;


}