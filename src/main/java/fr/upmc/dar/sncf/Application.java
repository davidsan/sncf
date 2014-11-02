package fr.upmc.dar.sncf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class Application implements EmbeddedServletContainerCustomizer {

	private static final Logger logger = LoggerFactory
			.getLogger(Application.class);

	@Override
	public void customize(ConfigurableEmbeddedServletContainerFactory factory) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("html", "text/html;charset=utf-8");
		factory.setMimeMappings(mappings);
	}

	public static void main(String[] args) throws Throwable {
		logger.info("Running...");
		SpringApplication.run(Application.class, args);
	}

}