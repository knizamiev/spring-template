package ru.template.dao;

import ru.template.model.User;

import java.util.List;

public interface UserDAO {

	void add(User user);

	List<User> findAllUsers();

	User findUser(long id);

	void updateUser(User user);

	void deleteUser(long id);
}
