package br.com.lamppit.accesscontrol.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.com.lamppit.core.entity.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user", schema = "system", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
@AuditTable(schema = "system_audit", value = "user_audit")
public class User extends EntityBase implements Serializable {

	private static final long serialVersionUID = 4358313336091674789L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	private String password;

	@Size(min = 3, message = "{field.size}")
	@NotNull(message = "{field.notnull}")
	@NotEmpty(message = "{field.notempty}")
	@Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ'\\s]+$", message = "{field.pattern.notNumber}")
	private String name;

	@Email(message = "{field.email}")
	@NotNull(message = "{field.notnull}")
	private String email;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Roles role;

	@ManyToOne
	@JoinColumn(name = "office_id")
	private Office office;

	private LocalDate AdmissionDate;

	private LocalDate ExpirationDate;

	@Builder
	public User(Long id, String password, String name, String email) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
	}

}
