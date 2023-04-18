package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}
	}

	public void update(User user) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionManager.getConnection();
			String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getPassword());
			preparedStatement.setString(2, user.getName());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getUserId());

			preparedStatement.executeUpdate();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}
	}

	public List<User> findAll() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionManager.getConnection();
			String sql = "SELECT userId, password, name, email FROM USERS";
			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			List<User> users = new ArrayList<>();

			while (resultSet.next()) {
				User user = new User(resultSet.getString("userId"), resultSet.getString("password"), resultSet.getString("name"),
					resultSet.getString("email"));

				users.add(user);
			}

			return users;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public User findByUserId(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
					rs.getString("email"));
			}

			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
}
