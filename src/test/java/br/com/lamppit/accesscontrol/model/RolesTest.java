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
class RolesTest extends EntityBase implements Serializable {
    private static final long serialVersionUID = -456933630959717010L;
    private Long id;
    private String name;


}