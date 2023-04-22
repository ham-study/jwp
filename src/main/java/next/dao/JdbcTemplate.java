package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public class JdbcTemplate {
	public <T> T queryForObject(String sql, ResultSetMapper<T> mapper, Object... parameters) {
		return execute(
			sql,
			pstmt -> {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}
				return pstmt.executeQuery();
			},
			resultSet -> {
				if (resultSet.next()) {
					return mapper.map(resultSet);
				}
				return null;
			}
		);
	}

	public <T> List<T> query(String sql, ResultSetMapper<T> mapper, Object... parameters) {
		return execute(
			sql,
			pstmt -> {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}

				return pstmt.executeQuery();
			},
			resultSet -> {
				List<T> result = new ArrayList<>();
				while (resultSet.next()) {
					result.add(mapper.map(resultSet));
				}

				return result;
			}
		);
	}

	public void update(String sql, Object... parameters) {
		execute(
			sql,
			pstmt -> {
				for (int i = 0; i < parameters.length; i++) {
					pstmt.setObject(i + 1, parameters[i]);
				}

				pstmt.executeUpdate();

				return null;
			}
		);
	}

	private <T> T execute(String sql, PreparedStatementExecutor executor, ResultSetProcessor<T> processor) {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {

			ResultSet resultSet = executor.execute(pstmt);

			return processor.process(resultSet);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void execute(String sql, PreparedStatementExecutor executor) {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {

			executor.execute(pstmt);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
