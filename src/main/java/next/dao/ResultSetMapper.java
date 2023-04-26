package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
interface ResultSetMapper<T> {
	T map(ResultSet resultSet) throws SQLException;
}
