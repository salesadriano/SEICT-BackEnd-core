package br.com.lamppit.app.model;

import java.io.Serializable;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import br.com.lamppit.core.model.BaseEntity;
import br.com.lamppit.core.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "people", schema = "app")
@Audited
@AuditTable(value = "people_audit", schema = "app_audit")
public class Person extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String name;

    @NotNull(message = "{field.notnull}")
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    
}
