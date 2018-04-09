package de.nieder.kick;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class KickApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(KickApplication.class);

	public void run(String... args) {
	}

	public static void main(String[] args) {
		SpringApplication.run(KickApplication.class, args);
	}

	@Configuration
	@Profile("local")
	@ComponentScan(lazyInit = true)
	public static class LocalConfig {
		public LocalConfig() {
			LOGGER.info("Lazy init");
		}
	}
}