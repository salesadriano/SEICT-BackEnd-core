package br.com.lamppit.accesscontrol.model.dto;

import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.model.ProfileActions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemsUserDTO {

    private Profile profile;
    private Iterable<ProfileActions> actions;
}
