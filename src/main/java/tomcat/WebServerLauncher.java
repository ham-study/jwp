package tomcat;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServerLauncher {
	private static final Logger logger = LoggerFactory.getLogger(WebServerLauncher.class);

	public static void main(String[] args) throws LifecycleException {
		String webappDirLocation = "webapp/";
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		logger.info("configuring app with basefdir:{}", new File(webappDirLocation).getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}
