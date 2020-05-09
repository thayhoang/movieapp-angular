package com.hoangmn;

import com.hoangmn.model.ERole;
import com.hoangmn.model.Role;
import com.hoangmn.model.User;
import com.hoangmn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
public class Application  {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@PostConstruct
	void setupData() {
		String password = encoder.encode("admin");
		User user = new User("admin", "admin@gmail.com", password);

		Role adminRole = new Role(ERole.ROLE_ADMIN);
		Role userRole = new Role(ERole.ROLE_USER);
		Role modRole = new Role(ERole.ROLE_MODERATOR);

		Set<Role> roles = new HashSet<Role>();
		roles.add(adminRole);
		roles.add(userRole);
		roles.add(modRole);
		user.setRoles(roles);

		userRepository.save(user);
	}

}
