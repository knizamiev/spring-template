package ru.template.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.template.dao.common.AbstractDAO;
import ru.template.dao.common.ExtendedBeanPropertySqlParameterSource;
import ru.template.dao.extractor.UserExtractor;
import ru.template.model.User;
import ru.template.model.UserHistory;

import javax.sql.DataSource;
import java.util.List;

import static ru.template.model.UserHistory.Status.CREATE;
import static ru.template.model.UserHistory.Status.DELETE;
import static ru.template.model.UserHistory.Status.UPDATE;


@Repository
public class UserDAOImpl extends AbstractDAO implements UserDAO {

	private static final BeanPropertyRowMapper<User> USER_ROW_MAPPER =
			new BeanPropertyRowMapper<>(User.class);
	private static final UserExtractor USER_EXTRACTOR = new UserExtractor();
	private final UserHistoryDAO userHistoryDAO;

	public UserDAOImpl(DataSource dataSource, UserHistoryDAO userHistoryDAO) {
		super(dataSource);
		this.userHistoryDAO = userHistoryDAO;
	}

	@Transactional
	@Override
	public void add(User user) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update("insert into users(" +
				"id, name, gender, date)" +
				"values (nextval('users_seq'), :name, :gender, :date)",
				new ExtendedBeanPropertySqlParameterSource(user),
				keyHolder,
				new String[]{"id"});

		userHistoryDAO.create(new UserHistory(keyHolder.getKey().longValue(), CREATE));
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

	@Transactional
	@Override
	public void updateUser(User user) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		String query = "update users set name = :name, gender = :gender, date = :date where id = :id";
		jdbcTemplate.update(query, new ExtendedBeanPropertySqlParameterSource(user), keyHolder,
				new String[]{"id"});

		userHistoryDAO.create(new UserHistory(keyHolder.getKey().longValue(), UPDATE));

	}

	@Transactional
	@Override
	public void deleteUser(long id) {
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update("delete from users where id = :id", new MapSqlParameterSource(map("id", id)),
				keyHolder, new String[]{"id"});

		userHistoryDAO.create(new UserHistory(keyHolder.getKey().longValue(), DELETE));
	}
}
