package br.com.lamppit.core.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "users")
public abstract class User extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4714829114589071076L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
	private String username;

	@NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String password;

	@NotNull(message = "{field.notnull}")
	@ManyToMany
	private List<Role> roles;

}
