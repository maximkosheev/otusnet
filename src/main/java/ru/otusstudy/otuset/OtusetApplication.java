package ru.otusstudy.otuset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OtusetApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtusetApplication.class, args);
	}

}
