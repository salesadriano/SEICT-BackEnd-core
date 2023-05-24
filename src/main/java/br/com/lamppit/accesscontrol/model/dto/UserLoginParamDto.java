package br.com.lamppit.accesscontrol.model.dto;

import lombok.Data;

@Data
public class UserLoginParamDto {

    private String email;
    private String password;
    private Long systemId;
}
