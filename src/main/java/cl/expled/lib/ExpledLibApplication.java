package cl.expled.lib;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExpledLibApplication {
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ExpledLibApplication.class, args);
		//System.out.println(ConfigProperties.getLocalProperty("propertiesFile"));
		//System.out.println(ConfigProperties.getProperty("driverDB"));
	}

}
