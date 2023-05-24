package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.ProfileActions;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.ProfileActionDTO;
import br.com.lamppit.accesscontrol.service.ProfileActionsService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profileActions")
public class ProfileActionsController extends Controller<ProfileActions, Long> {

    @Autowired
    protected ProfileActionsService profileActionsService;

    @Override
    protected ProfileActionsService getService() {
        return profileActionsService;
    }


    @GetMapping("/findByProfileSystems/{profileSystemsId}")
    public Iterable<ProfileActions> getByProfileSystems_Id(@PathVariable Long profileSystemsId) {
        return getService().findByProfileSystemsId(profileSystemsId);
    }

    @PutMapping("/changeStatus/{profileActionsId}")
    public MessageDTO changeStatus(@PathVariable Long profileActionsId) {
        return getService().changeStatus(profileActionsId);
    }

    @GetMapping("/getActionsByProfileSystemId/{profileSystemsId}")
    public Iterable<EntityDTO> getActionsByProfileSystemsId(@PathVariable Long profileSystemsId) {
        return getService().getActionsByProfileSystemsId(profileSystemsId);
    }

    @GetMapping("/getProfileActionsByProfileSystemsId/{profileSystemsId}")
    public Iterable<ProfileActionDTO> getProfileActionsByProfileSystemsId(@PathVariable Long profileSystemsId) {
        return getService().getProfileActionsByProfileSystemsId(profileSystemsId);
    }

}
