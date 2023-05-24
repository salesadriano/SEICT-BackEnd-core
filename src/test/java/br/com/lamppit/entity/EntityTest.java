package br.com.lamppit.entity;

import java.io.Serializable;

import br.com.lamppit.core.entity.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@Data
public class EntityTest extends EntityBase implements Serializable {
	private static final long serialVersionUID = 7867651620471122855L;
	private Integer id;
	private String name;
}
