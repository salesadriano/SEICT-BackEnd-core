package br.com.lamppit.accesscontrol.model;

import br.com.lamppit.core.entity.EntityBase;
import lombok.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;


@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemsTest extends EntityBase implements Serializable {
    private static final long serialVersionUID = 7867651620471122855L;
    private Long id;
    private String name;




}