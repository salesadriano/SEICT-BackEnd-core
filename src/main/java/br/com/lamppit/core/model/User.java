package br.com.lamppit.core.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "users", schema = "commons")
@Audited
@AuditTable(value = "users_audit", schema = "commons_audit")
public class User extends BaseEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
	private String username;

	@NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable( 
		name = "users_roles",
		schema = "commons", 
		joinColumns = @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	@AuditJoinTable(
		name = "users_roles_audit", 
		schema = "commons_audit")
	private List<Role> roles;

}
