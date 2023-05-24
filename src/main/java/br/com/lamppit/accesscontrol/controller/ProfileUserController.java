package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.ProfileUser;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.service.ProfileUserService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profileUser")
public class ProfileUserController extends Controller<ProfileUser, Long> {

    @Autowired
    protected ProfileUserService profileUserService;

    @Override
    protected ProfileUserService getService() {
        return profileUserService;
    }

    @GetMapping
    public Iterable<ProfileUser> getAll() {
        return getService().findAllProfileUser();
    }

    @PutMapping("/changeStatus/{profileUserId}")
    public MessageDTO changeStatus(@PathVariable Long profileUserId) {
        return getService().changeStatus(profileUserId);
    }

    @GetMapping("/getByUserId/{userId}")
    public Iterable<ProfileUser> getByUserId(@PathVariable Long userId) {
        return getService().findByUserId(userId);
    }

    @GetMapping("/getSystemByUserId/{userId}")
    public Iterable<EntityDTO> getSystemByUserId(@PathVariable Long userId) {
        return getService().getSystemByUserId(userId);
    }

    @GetMapping("/getByUserIdAndSystemId/{userId}/{systemId}")
    public Iterable<EntityDTO> getByUserIdAndSystemId(@PathVariable Long userId, @PathVariable Long systemId) {
        return getService().getByUserIdAndSystemId(userId, systemId);
    }

}
