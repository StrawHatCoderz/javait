package com.javait;

import com.javait.models.User;
import com.javait.models.Users;
import com.javait.repos.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaitApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaitApplication.class, args);

		Users users = new Users();
		UserRepo userRepo = new UserRepo(users);

		User user = userRepo.createUser(1, "Deadpool", "https://deadpool.com");
		System.out.println(user.toString());
	}
}
