package com.restapi.users;

import com.restapi.users.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class AblunApplication {

	public static void main(String[] args) {
		SpringApplication.run(AblunApplication.class, args);
	}

}
