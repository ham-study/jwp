package next.support.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import next.dispatch.ApplicationConfiguration;
import next.dispatch.DispatcherServlet;
import next.dispatch.RequestMapping;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());

        configApplication(sce.getServletContext());

        logger.info("Completed Load ServletContext!");
    }

    private void configApplication(ServletContext servletContext) {
        ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
        RequestMapping requestMapping = new RequestMapping();

        applicationConfiguration.addHandlers(requestMapping);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(requestMapping);

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", dispatcherServlet);
        dynamic.setLoadOnStartup(1);
        dynamic.addMapping("/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
