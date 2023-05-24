package br.com.lamppit.accesscontrol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileActionDTO {

    private Long id;

    private Long actionId;
    private String actionName;

    private Long profileId;
    private String profileName;

    private Long systemId;
    private String systemName;

}
