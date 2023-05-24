package br.com.lamppit.accesscontrol.controller;

import br.com.lamppit.accesscontrol.model.dto.UserAccesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.lamppit.accesscontrol.model.User;
import br.com.lamppit.accesscontrol.service.UserService;
import br.com.lamppit.core.controller.Controller;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends Controller<User, Long> {

	@Autowired
	protected UserService userService;

	@Override
	protected UserService getService() {
		return userService;
	}

	@GetMapping
	public List<User> getAll() {
		return userService.findAllUsers();
	}

	@GetMapping("/getAllAccess/{userId}")
	public List<UserAccesDTO> getAllAccess(@PathVariable @NotNull Long userId) {
		return userService.findAllAccess(userId);
	}

}
