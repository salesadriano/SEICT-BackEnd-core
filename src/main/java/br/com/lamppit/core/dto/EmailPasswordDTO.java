package br.com.lamppit.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailPasswordDTO {
    private String name;
    private String system;
    private String dateOcorrence;
    private String url;
    private String email;
}
