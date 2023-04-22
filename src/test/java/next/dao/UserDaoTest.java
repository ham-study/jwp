package next.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDaoTest {
	private UserDao sut;

	private User user;

	@BeforeAll
	static void createTable() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
	}

	@BeforeEach
	void setUp() {
		sut = new UserDao(new JdbcTemplate());
		user = new User("admin", "password", "자바지기", "admin@slipp.net");
	}

	@Test
	void testUpdate() {
		// given
		User expected = new User(user.getUserId(), "new password", "new name", "new email");

		// when
		sut.update(expected);
		User result = sut.findByUserId(user.getUserId());

		// then
		assertThat(result.getUserId()).isEqualTo(expected.getUserId());
		assertThat(result.getPassword()).isEqualTo(expected.getPassword());
		assertThat(result.getName()).isEqualTo(expected.getName());
		assertThat(result.getEmail()).isEqualTo(expected.getEmail());
	}

	@Test
	void testFindAll() {
		// given
		List<User> expected = new ArrayList<>() {{
			add(user);
		}};

		// when
		List<User> result = sut.findAll();

		// then
		assertThat(result).usingRecursiveFieldByFieldElementComparatorOnFields("userId", "password", "name", "email")
			.containsExactlyElementsOf(expected);

	}
}
