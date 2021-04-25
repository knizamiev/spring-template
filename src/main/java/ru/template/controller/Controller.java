package ru.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.template.dao.UserDAO;
import ru.template.dto.UserDTO;
import ru.template.model.User;
import ru.template.service.UserService;

import java.util.Date;
import java.util.List;

@RestController
public class Controller {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserService userService;

	@PostMapping("/add")
	public void addUsers(@RequestBody User user) {
		user.setDate(new Date());
		userDAO.add(user);
	}

	@GetMapping("/users")
	public List<UserDTO> findAllUsers() {
			return userService.getAllUsers();
	}

	@GetMapping("/user/{id}")
	public UserDTO findAllUsers(@PathVariable long id) {
		return userService.getUser(id);
	}

	@PostMapping("/update")
	public void updateUser(@RequestBody User user){
		user.setDate(new Date());
		userDAO.updateUser(user);
	}

	@PostMapping("/drop/{id}")
	public void drop(@PathVariable long id){
		userDAO.deleteUser(id);
	}
}
