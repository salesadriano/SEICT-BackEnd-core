package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.AuthenticationUser;
import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.model.dto.ChangePasswordParamDTO;
import br.com.lamppit.accesscontrol.model.dto.UserLoginDto;
import br.com.lamppit.accesscontrol.model.dto.UserLoginParamDto;
import br.com.lamppit.accesscontrol.service.AuthenticateUserService;
import br.com.lamppit.core.controller.Controller;
import br.com.lamppit.core.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authenticationUser")
public class AuthenticationUserController extends Controller<AuthenticationUser, Long> {


    @Autowired
    private AuthenticateUserService service;


    @Override
    protected AuthenticateUserService getService() {
        return service;
    }

    @PostMapping("/recoverPassword/{systemId}")
    public MessageDTO login(@RequestBody User user, @PathVariable Long systemId) throws Exception {
        return service.recoverPassword(user.getEmail(), systemId);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDto> login(@RequestBody UserLoginParamDto loginParam) throws Exception {


        UserLoginDto dto = service.login(loginParam.getEmail(), loginParam.getPassword(), loginParam.getSystemId());

        HttpHeaders header = new HttpHeaders();
        header.set("Authorization", dto.getToken());

//        dto.setToken(null);

        return ResponseEntity.ok().headers(header).body(dto);
    }

    @PostMapping("/changePassword")
    public MessageDTO changePassword(@RequestBody ChangePasswordParamDTO param) {
        return service.changePassword(param);
    }


}
