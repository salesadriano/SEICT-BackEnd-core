package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Audited

@AuditTable(schema = "system_audit", value = "profile_action_audit")
@Table(name = "profile_actions", schema = "system", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"profile_systems_id", "action_id"})})
public class ProfileActions extends EntityBase implements Serializable {

    private static final long serialVersionUID = -6386270081908458322L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "{field.notnull}")
    @JoinColumn(name = "profile_systems_id")
    private ProfileSystems profileSystems;

    @ManyToOne
    @NotNull(message = "{field.notnull}")
    @JoinColumn(name = "action_id")
    private Action action;

    @Builder.Default
    private boolean isertable = false;

    @Builder.Default
    private boolean updatable = false;;

    @Builder.Default
    private boolean deletable = false;;

    @Builder.Default
    private boolean readable = false;;

    @Builder.Default
    private boolean auditable = false;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    private LocalDateTime deactivatedAt;

}