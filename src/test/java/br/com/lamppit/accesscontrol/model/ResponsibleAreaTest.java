package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
class ResponsibleAreaTest extends EntityBase implements Serializable {
    private static final long serialVersionUID = 7867651620471122855L;
    private Long id;
    private String nameArea;
    private String accountable;

}