package br.com.lamppit.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lamppit.core.model.Role;
import br.com.lamppit.core.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RolesController extends Controller<Role, Long> {

    @Autowired
    protected RoleService roleService;

    @Override
    protected RoleService getService() {
        return roleService;
    }

}
