package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Audited
@AuditTable(schema = "system_audit", value = "roles_audit")
@Entity(name = "Profile")
@Table(name = "profile", schema = "system")
public class Profile extends EntityBase implements Serializable {

    private static final long serialVersionUID = 8072006766127240755L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String name;

    private String description;

}
