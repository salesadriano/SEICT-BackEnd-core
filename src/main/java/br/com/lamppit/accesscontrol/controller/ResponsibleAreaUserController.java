package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.ResponsibleAreaUser;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.UserRoleDTO;
import br.com.lamppit.accesscontrol.service.ResponsibleAreaUserService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsibleAreaUser")
public class ResponsibleAreaUserController extends Controller<ResponsibleAreaUser, Long> {

    @Autowired
    protected ResponsibleAreaUserService responsibleAreaUserService;

    @Override
    protected ResponsibleAreaUserService getService() {
        return responsibleAreaUserService;
    }

    @GetMapping
    public Iterable<ResponsibleAreaUser> findAll() {
        return getService().findAll(Sort.by("id"));
    }

    @GetMapping("/getByResponsibleArea_Id/{id}")
    public Iterable<ResponsibleAreaUser> getByResponsibleArea_Id(@PathVariable Long id) {
        return getService().findByResponsibleArea_Id(id, Sort.by("id"));
    }

    @GetMapping("/getByUserId/{userId}")
    public Iterable<ResponsibleAreaUser> getByUser_Id(@PathVariable Long userId) {
        return getService().findByUserId(userId);
    }

    @GetMapping("/getUserByResponsibleAreaId/{responsibleAreaId}")
    public Iterable<UserRoleDTO> getUserByResponsibleAreaId(@PathVariable Long responsibleAreaId) {
        return getService().getUserByResponsibleAreaId(responsibleAreaId);
    }

    @PutMapping("/changeStatus/{responsibleAreaUserId}")
    public MessageDTO changeStatus(@PathVariable Long responsibleAreaUserId) {
        return getService().changeStatus(responsibleAreaUserId);
    }

}
