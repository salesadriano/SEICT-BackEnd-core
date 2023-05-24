package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.Systems;
import br.com.lamppit.accesscontrol.service.SystemService;
import br.com.lamppit.core.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/systems")
public class SystemController extends Controller<Systems, Long> {

    @Autowired
    protected SystemService systemService;

    @Override
    protected SystemService getService() {
        return systemService;
    }

    @GetMapping
    public Iterable<Systems> getAll() {
        return getService().findAllSystems();
    }

}

