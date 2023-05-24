package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.Roles;
import br.com.lamppit.accesscontrol.service.RolesService;
import br.com.lamppit.core.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RolesController extends Controller<Roles, Long> {

    @Autowired
    protected RolesService rolesService;

    @Override
    protected RolesService getService() {
        return rolesService;
    }

    @GetMapping
    public Iterable<Roles> getAll() {
        return getService().findAllRoles();
    }

}

