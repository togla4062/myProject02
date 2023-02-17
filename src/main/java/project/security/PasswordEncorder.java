package project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncorder {
	
	/* 20230109 문대현 생성 */
	
	  @Bean
	  PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
}
