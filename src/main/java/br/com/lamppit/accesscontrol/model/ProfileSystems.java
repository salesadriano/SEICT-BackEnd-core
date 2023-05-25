package br.com.lamppit.accesscontrol.model;

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
@AuditTable(schema = "system_audit", value = "profile_systems_audit")
@Table(name = "profile_systems", schema = "system", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"profile_id", "system_id"})})
public class ProfileSystems implements Serializable {

    private static final long serialVersionUID = 3811129231999510749L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @NotNull(message = "{field.notnull}")
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @NotNull(message = "{field.notnull}")
    @JoinColumn(name = "system_id")
    private Systems system;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    private LocalDateTime deactivatedAt;


}