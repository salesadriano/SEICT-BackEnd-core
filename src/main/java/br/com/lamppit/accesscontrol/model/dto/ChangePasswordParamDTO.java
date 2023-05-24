package br.com.lamppit.accesscontrol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordParamDTO {

    private Long userId;
    private String password;
    private String token;
}
