package fr.upmc.dar.sncf;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "fr")
public class AppConfiguration extends WebMvcAutoConfiguration {

	@Bean
	public BasicDataSource dataSource() throws URISyntaxException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
				+ dbUri.getPort() + dbUri.getPath();

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);

		return basicDataSource;
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {

		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(
					ConfigurableEmbeddedServletContainerFactory container) {

				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED,
						"/error");
				ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN,
						"/error");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND,
						"/error");
				ErrorPage error500Page = new ErrorPage(
						HttpStatus.INTERNAL_SERVER_ERROR, "/error");
				ErrorPage error503Page = new ErrorPage(
						HttpStatus.SERVICE_UNAVAILABLE, "/error");

				container.addErrorPages(error401Page, error403Page,
						error404Page, error500Page, error503Page);
			}
		};
	}
}
