package br.com.lamppit.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lamppit.core.exception.EntityValidationException;
import br.com.lamppit.core.model.User;
import br.com.lamppit.core.service.UserService;
import br.com.lamppit.core.util.JsonMapperUtil;
import br.com.lamppit.core.util.JwtUtilities;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController extends Controller<User, Long> {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    protected UserService userService;

    @Autowired
    private JwtUtilities jwtUtilities;

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

    @PostMapping("/login")
    public String login(@RequestBody User user) throws Exception {
        User u = getService().findByUsername(user.getUsername());

        if (u == null || !encoder.matches(user.getPassword(), u.getPassword())) {
            throw new EntityValidationException("Usuário ou senha inválidos");
        }

        String jsonReturn = JsonMapperUtil.mapToJson(u);

        String token = jwtUtilities.createJWT(user.getId().toString(), user.getUsername(), jsonReturn, new Date().getTime() + 3600000);

        return token;
    }

}
