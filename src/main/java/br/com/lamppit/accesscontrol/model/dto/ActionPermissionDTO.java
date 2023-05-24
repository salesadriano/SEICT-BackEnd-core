package br.com.lamppit.accesscontrol.model.dto;

import br.com.lamppit.accesscontrol.model.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionPermissionDTO {

    private Action action;

    private boolean isertable = false;

    private boolean updatable = false;;

    private boolean deletable = false;;

    private boolean readable = false;;

    private boolean auditable = false;
}
