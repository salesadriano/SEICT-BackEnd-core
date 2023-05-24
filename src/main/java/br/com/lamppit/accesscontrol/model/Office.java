package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Audited
@Table(name = "office", schema = "system")
@AuditTable(schema = "system_audit", value = "office_audit")
public class Office extends EntityBase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "{field.notnull}")
    @NotEmpty(message = "{field.notempty}")
    private String name;

    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Office office = (Office) o;
        return id != null && Objects.equals(id, office.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}