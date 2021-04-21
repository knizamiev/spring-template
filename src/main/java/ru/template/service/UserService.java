package ru.template.service;

import ru.template.dto.UserDTO;

import java.util.List;

public interface UserService {

	List<UserDTO> getAllUsers();

	UserDTO getUser(long id);
}
