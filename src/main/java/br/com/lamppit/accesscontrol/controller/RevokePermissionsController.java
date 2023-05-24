package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.RevokePermissions;
import br.com.lamppit.accesscontrol.service.RevokePermissionsService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revokePermissions")
public class RevokePermissionsController extends Controller<RevokePermissions, Long> {

    @Autowired
    protected RevokePermissionsService revokePermissionsService;

    @Override
    protected RevokePermissionsService getService() {
        return revokePermissionsService;
    }

    @GetMapping
    public Iterable<RevokePermissions> getAll() {
        return getService().findAllRevokePermissions();
    }

    @GetMapping("/getByUserId/{userId}")
    public Iterable<RevokePermissions> getByUserId(@PathVariable Long userId) {
        return getService().findByUserId(userId);
    }

    @PutMapping("/changeStatus/{revokePermissionsId}")
    public MessageDTO changeStatus(@PathVariable Long revokePermissionsId) {
        return getService().changeStatus(revokePermissionsId);
    }

}
