package br.com.lamppit.app;

import java.io.Serializable;

import br.com.lamppit.core.entity.EntityBase;
import br.com.lamppit.core.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Person extends EntityBase implements Serializable {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String name;


}
