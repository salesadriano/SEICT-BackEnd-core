package br.com.lamppit.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lamppit.app.model.Person;
import br.com.lamppit.app.service.PersonService;
import br.com.lamppit.core.controller.Controller;

@RestController
@RequestMapping("/people")
public class PeopleController extends Controller<Person, Long> {

    @Autowired
    protected PersonService personService;

    @Override
    protected PersonService getService() {
        return personService;
    }

}
