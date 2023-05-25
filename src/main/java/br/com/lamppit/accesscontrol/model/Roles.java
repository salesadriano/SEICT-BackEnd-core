package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.accesscontrol.model.enumerated.ImportantRoles;
import br.com.lamppit.accesscontrol.serializer.ImportantRolesDeserializer;
import br.com.lamppit.core.entity.EntityBase;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Audited
@AuditTable(schema = "system_audit", value = "roles_audit")
@Entity(name = "Roles")
@Table(name = "roles", schema = "system")
public class Roles extends EntityBase implements Serializable {
    private static final long serialVersionUID = -456933630959717010L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    @Column(name = "name", unique = true)
    private String name;

    private String observation;

    @Enumerated(EnumType.ORDINAL)
    @JsonDeserialize(using = ImportantRolesDeserializer.class)
    private ImportantRoles importantRoles;


}