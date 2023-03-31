package next.support.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.db.DataBase;
import core.jdbc.ConnectionManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import next.model.User;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());

		User user = new User("user", "1234", "함승훈", "dau47@naver.com");
		DataBase.addUser(user);
		logger.info("admin user is added!{}", user);

		logger.info("Completed Load ServletContext!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
