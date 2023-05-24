package br.com.lamppit.accesscontrol.model.dto;

import br.com.lamppit.accesscontrol.model.Systems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccesDTO {

    private Systems systems;
    private List<SystemsUserDTO> profileSystems;

    public Systems getSystems() {



        return systems;
    }
}
