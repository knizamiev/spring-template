package ru.template.dao.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.template.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserExtractor implements ResultSetExtractor<List<User>> {
	//	if you need abrakadabra
	@Override
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<User> result = new ArrayList<>();
 		while (rs.next()){
			User user = new User();

			user.setId(rs.getLong("id"));
			user.setName(rs.getString("name"));
			user.setGender(rs.getString("gender"));
			user.setDeleted(rs.getBoolean("deleted"));
			user.setDate(rs.getDate("date"));

			result.add(user);
		}
		return result;
	}
}
