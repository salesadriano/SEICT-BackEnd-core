package br.com.lamppit.accesscontrol.model.dto;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    private Long id;
    private String name;
    private String role;
}