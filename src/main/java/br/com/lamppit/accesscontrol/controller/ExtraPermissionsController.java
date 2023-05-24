package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.ExtraPermissions;
import br.com.lamppit.accesscontrol.service.ExtraPermissionsService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extraPermissions")
public class ExtraPermissionsController extends Controller<ExtraPermissions, Long> {

    @Autowired
    protected ExtraPermissionsService extraPermissionsService;

    @Override
    protected ExtraPermissionsService getService() {
        return extraPermissionsService;
    }

    @GetMapping
    public Iterable<ExtraPermissions> getAll() {
        return getService().findAllExtraPermissions(Sort.by("profileAction.profileSystem.system.name").ascending());
    }

    @GetMapping("/getByUser/{userId}")
    public Iterable<ExtraPermissions> getByUserId(@PathVariable Long userId) {
        return getService().findByUserId(userId);
    }

    @PutMapping("/changeStatus/{extraPermissionsId}")
    public MessageDTO changeStatus(@PathVariable Long extraPermissionsId) {
        return getService().changeStatus(extraPermissionsId);
    }

}