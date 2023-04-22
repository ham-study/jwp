package next.dao;

import java.util.List;

import next.model.User;

public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(User user) {
        String sql = "INSERT INTO USERS(userId, password, name, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        String sql = """
                    UPDATE USERS
                    SET password = ?
                        , name = ?
                        , email = ?
                    WHERE userId = ?
                """;
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        String sql = """
            SELECT userId, password, name, email
             FROM USERS
             """;

        return jdbcTemplate.query(sql, (resultSet -> {
            String userId = resultSet.getString("userId");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");

            return new User(userId, password, name, email);
        }));
    }

    public User findByUserId(String userId) {
        String sql = """
            SELECT userId, password, name, email
            FROM USERS
            WHERE userid=?
            """;

        return jdbcTemplate.queryForObject(sql, (resultSet -> {
            String id = resultSet.getString("userId");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");

            return new User(id, password, name, email);
        }), userId);
    }
}
