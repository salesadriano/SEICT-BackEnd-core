package br.com.lamppit.accesscontrol.model.dto;

import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginDto {

    private User user;
    private List<Profile> profiles;
    private List<ActionPermissionDTO> actions;
    private String token;
    private boolean isManager;
    private boolean isRhManager;
}
