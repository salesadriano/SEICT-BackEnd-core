package br.com.lamppit.accesscontrol.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBySystemDTO {

    private Long id;
    private String nome;
}
