package ru.template.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import ru.template.dao.common.AbstractDAO;
import ru.template.dao.common.ExtendedBeanPropertySqlParameterSource;
import ru.template.dao.extractor.UserExtractor;
import ru.template.model.User;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAO implements UserDAO  {

	private static final BeanPropertyRowMapper<User> USER_ROW_MAPPER =
			new BeanPropertyRowMapper<>(User.class);

	private static final UserExtractor USER_EXTRACTOR = new UserExtractor() ;

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

	@Override
	public List<User> findAllUsers() {
		return jdbcTemplate.query("select * from users", USER_EXTRACTOR);
	}

	@Override
	public User findUser(long id) {
		return jdbcTemplate.queryForObject("select * from users where id = :id ",
				map("id", id), USER_ROW_MAPPER);
	}

	@Override
	public void updateUser(long id, User user) {
		ExtendedBeanPropertySqlParameterSource params = new ExtendedBeanPropertySqlParameterSource(user);
		String query = "update users set name = :name, gender = :gender, date = :date where id = :id";
		jdbcTemplate.update(query, params);

	}

	@Override
	public void deleteUser(long id) {
		jdbcTemplate.update("delete from users where id = :id", map("id", id));
	}
}
