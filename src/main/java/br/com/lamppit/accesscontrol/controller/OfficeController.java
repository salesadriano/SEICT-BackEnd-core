package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.Office;
import br.com.lamppit.accesscontrol.service.OfficeService;
import br.com.lamppit.core.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/office")
public class OfficeController extends Controller<Office, Long> {

    @Autowired
    protected OfficeService officeService;

    @Override
    protected OfficeService getService() {
        return officeService;
    }

    @GetMapping
    public Iterable<Office> getAll() {
        return getService().findAllOffice();
    }

}
