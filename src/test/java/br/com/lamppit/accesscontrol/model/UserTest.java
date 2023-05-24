package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
class UserTest extends Operator {

    private static final long serialVersionUID = -4714829114589071076L;

    private Long id;
    private String login;
    private String password;
    private String name;

}