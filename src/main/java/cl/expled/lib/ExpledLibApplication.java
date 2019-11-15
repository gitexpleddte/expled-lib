package cl.expled.lib;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cl.expled.lib.ExpledLibApplication;

@SpringBootApplication
public class ExpledLibApplication {
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ExpledLibApplication.class, args);
	}

}
