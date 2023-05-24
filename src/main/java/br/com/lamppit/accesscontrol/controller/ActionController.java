package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.Action;
import br.com.lamppit.accesscontrol.service.ActionService;
import br.com.lamppit.core.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action")
public class ActionController extends Controller<Action, Long> {

    @Autowired
    protected ActionService actionService;

    @Override
    protected ActionService getService() {
        return actionService;
    }

    @GetMapping
    public Iterable<Action> getAll() {
        return getService().findAllAction();
    }

    @GetMapping("/getByName/{name}")
    public Iterable<Action> getByName(@PathVariable String name) {
        return getService().findByName(name);
    }

}
