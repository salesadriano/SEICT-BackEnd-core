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
@AuditTable(schema = "system_audit", value = "profile_user_audit")
@Table(name = "profile_user", schema = "system", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "profile_systems_id"})})
public class ProfileUser extends EntityBase implements Serializable {

    private static final long serialVersionUID = -4332230876606450198L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_systems_id")
    @NotNull(message = "{field.notnull}")
    private ProfileSystems profileSystems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "{field.notnull}")
    private User user;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    private LocalDateTime deactivatedAt;

}



