package br.com.bradesco.codebankers.scam_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CodebrankersScamApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodebrankersScamApiApplication.class, args);
	}

}
