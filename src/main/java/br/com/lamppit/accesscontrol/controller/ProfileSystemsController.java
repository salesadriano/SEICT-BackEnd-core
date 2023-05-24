package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.ProfileSystems;
import br.com.lamppit.accesscontrol.service.ProfileSystemsService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profileSystems")
public class ProfileSystemsController extends Controller<ProfileSystems, Long> {

    @Autowired
    protected ProfileSystemsService profileSystemsService;

    @Override
    protected ProfileSystemsService getService() {
        return profileSystemsService;
    }


    @GetMapping("/getBySystemId/{systemId}")
    public Iterable<ProfileSystems> getProfileBySystem(@PathVariable Long systemId) {
        return getService().findBySystem_Id(systemId, Sort.by("profile.id"));
    }

    @PutMapping(value = "/changeStatus/{profileSystemsId}")
    public MessageDTO changeStatus(@PathVariable Long profileSystemsId) {
        return getService().changeStatus(profileSystemsId);
    }

}
