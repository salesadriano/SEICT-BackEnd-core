package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.enumerated.EnumSystemType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enumSystem")
public class EnumSystemController {

    @GetMapping
    public EnumSystemType[] getEnumSystemType() {
        return EnumSystemType.values();
    }

}
