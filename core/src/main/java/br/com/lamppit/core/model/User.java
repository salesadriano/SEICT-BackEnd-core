package br.com.lamppit.core.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Table(name = "users", schema = "commons")
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
	private List<Role> roles;

}
