package ru.template.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.template.dao.UserDAO;
import ru.template.dto.UserDTO;
import ru.template.exception.ApiRequestExeption;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	private final ModelMapper modelMapper;

	public UserServiceImpl() {
		this.modelMapper = new ModelMapper();
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return userDAO.findAllUsers().stream()
				.map(model -> modelMapper.map(model, UserDTO.class)).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUser(long id) {
		return modelMapper.map(userDAO.findUser(id), UserDTO.class);
	}
}
