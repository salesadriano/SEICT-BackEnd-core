package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.Profile;
import br.com.lamppit.accesscontrol.service.ProfileService;
import br.com.lamppit.core.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfilesController extends Controller<Profile, Long> {

    @Autowired
    protected ProfileService profileService;

    @Override
    protected ProfileService getService() {
        return profileService;
    }

    @GetMapping
    public Iterable<Profile> getAll() {
        return getService().findAll();
    }

    @GetMapping("/getByName/{name}")
    public Iterable<Profile> getByName(@PathVariable String name) {
        return getService().findByName(name);
    }

}
