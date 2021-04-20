package ru.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.template.dao.UserDAO;
import ru.template.model.User;

import java.util.Date;

@RestController
public class Controller {

	@Autowired
	private UserDAO userDAO;

	@GetMapping("/hello")
	public String hello(){
		return "hello";
	}
	@PostMapping("/add")
	public void addUsers(@RequestBody User user){
		user.setDate(new Date());
		userDAO.add(user);
	}
}
