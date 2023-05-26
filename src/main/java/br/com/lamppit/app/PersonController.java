package br.com.lamppit.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lamppit.core.controller.Controller;

@RestController
@RequestMapping("/person")
public class PersonController extends Controller<Person, Long> {

    @Autowired
    private PersonService userService;

    @Override
    protected PersonService getService() {
        return userService;
    }
    
}
