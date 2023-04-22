package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementExecutor {
	ResultSet execute(PreparedStatement statement) throws SQLException;
}
