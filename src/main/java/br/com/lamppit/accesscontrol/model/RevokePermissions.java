package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited
@AuditTable(schema = "system_audit", value = "revoke_permissions_audit")
@Table(name = "revoke_permissions", schema = "system", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "profile_action_id"})})
public class RevokePermissions extends EntityBase implements Serializable {

    private static final long serialVersionUID = 6256222866491583324L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "{field.notnull}")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NotNull(message = "{field.notnull}")
    @JoinColumn(name = "profile_action_id")
    private ProfileActions profileAction;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    private LocalDateTime deactivatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RevokePermissions that = (RevokePermissions) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}