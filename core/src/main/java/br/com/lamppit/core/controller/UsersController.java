package br.com.lamppit.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.model.User;
import br.com.lamppit.core.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController extends Controller<User, Long> {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    protected UserService userService;

    @Override
    protected UserService getService() {
        return userService;
    }

	@Override
    @PostMapping
	protected User newEntity(@Valid @RequestBody User user) throws EntityValidationException {
        user.setPassword(encoder.encode(user.getPassword()));

		return getService().save(user);
	}

}
