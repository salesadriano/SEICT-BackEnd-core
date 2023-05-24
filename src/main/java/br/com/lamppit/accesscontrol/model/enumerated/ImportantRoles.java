package br.com.lamppit.accesscontrol.model.enumerated;

import br.com.lamppit.accesscontrol.serializer.ImportantRolesSerializer;
import br.com.lamppit.core.exception.EntityValidationException;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = ImportantRolesSerializer.class)
public enum ImportantRoles {

    MANAGER(0, "ROLE_MANAGER"),
    RH_MANAGER(1, "ROLE_RH_MANAGER");

    private Integer id;
    private String description;

    public static ImportantRoles getById(Integer id) {
        for (ImportantRoles importantRoles : ImportantRoles.values()) {
            if (importantRoles.getId().equals(id)) {
                return importantRoles;
            }
        }
        String msg = "except.error.notexist.message.type";
        throw new EntityValidationException(msg);
    }
}
