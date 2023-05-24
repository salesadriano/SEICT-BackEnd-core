package br.com.lamppit.accesscontrol.model;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Table(name = "action", schema = "system")
@AuditTable(schema = "system_audit", value = "action_audit")
public class Action extends EntityBase implements Serializable {

    private static final long serialVersionUID = 1816201119371289249L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String name;

    private String description;


}
