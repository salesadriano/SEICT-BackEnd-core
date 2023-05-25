package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Audited
@AuditTable(schema = "system_audit", value = "authentication_user_audit")
@Table(name = "authentication_user" , schema = "system")
public class AuthenticationUser extends EntityBase implements Serializable {

    private static final long serialVersionUID = -2980524856044974255L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String token;

    private boolean used;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}