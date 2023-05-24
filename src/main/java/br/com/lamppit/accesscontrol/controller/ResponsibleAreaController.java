package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.ResponsibleArea;
import br.com.lamppit.accesscontrol.model.dto.EntityDTO;
import br.com.lamppit.accesscontrol.model.dto.UserRoleDTO;
import br.com.lamppit.accesscontrol.service.ResponsibleAreaService;
import br.com.lamppit.core.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/responsibleArea")
public class ResponsibleAreaController extends Controller<ResponsibleArea, Long> {

    @Autowired
    protected ResponsibleAreaService responsibleAreaService;

    @Override
    protected ResponsibleAreaService getService() {
        return responsibleAreaService;
    }

    @GetMapping
    public Iterable<ResponsibleArea> getAll() {
        return getService().findAllArea();
    }

    @GetMapping("/getByManager/{idManager}")
    public Iterable<EntityDTO> getByManager(@PathVariable Long idManager) {
        return getService().getByManager(idManager);
    }

    @GetMapping("/getManagerByResponsibleArea/{responsibleAreaId}")
    public Iterable<UserRoleDTO> getManagerByResponsibleArea(@PathVariable Long responsibleAreaId) {
        return getService().getManagerByResponsibleArea(responsibleAreaId);
    }

}
