package br.com.lamppit.core.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class Operator extends EntityBase implements Serializable {
	private static final long serialVersionUID = -4714829114589071076L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "{field.notnull}")
	private String login;


}
