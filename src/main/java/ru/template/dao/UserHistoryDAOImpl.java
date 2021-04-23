package ru.template.dao;

import org.springframework.stereotype.Repository;
import ru.template.dao.common.AbstractDAO;
import ru.template.model.UserHistory;

import javax.sql.DataSource;

@Repository
public class UserHistoryDAOImpl extends AbstractDAO implements UserHistoryDAO {

	public UserHistoryDAOImpl(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void create(UserHistory userHistory) {
		jdbcTemplate.update("insert into users_history (id, user_id, status)" +
				"values (nextval('users_history_seq'), :userId, :status)", map("userId", userHistory.getUserId(),
				"status", userHistory.getStatus().toString()));
	}
}
