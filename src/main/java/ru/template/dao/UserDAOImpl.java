package ru.template.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import ru.template.dao.common.AbstractDAO;
import ru.template.model.User;

import javax.sql.DataSource;
@Repository
public class UserDAOImpl extends AbstractDAO implements UserDAO  {

	private static final BeanPropertyRowMapper<User> USER_ROW_MAPPER =
			new BeanPropertyRowMapper<>(User.class);

	public UserDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void add(User user) {
		jdbcTemplate.update("insert into users(" +
				"id, name, gender, date)" +
				"values (nextval('users_seq'), :name, :gender, :date)", map("name", user.getName(),
				"gender", user.getGender(), "date", user.getDate()));
	}
}
