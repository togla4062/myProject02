package project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
	
	/* 20230109 문대현 수정 */
	/* 20230120 김한아 수정 */
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(

				authorize -> authorize
					.antMatchers("/js/**", "/image/**", "/css/**", "/resigned")
					.permitAll() // 허용해야하는 url
					//.anyRequest().authenticated())
					.anyRequest().hasAnyAuthority("ROLE_PERSONAL", "ROLE_EMPLOYEE", "ROLE_CEO")) //퇴직한 사원은 접근불가 230126한아
				.formLogin(formLogin -> formLogin
					.loginPage("/login")
					.loginProcessingUrl("/login") // form action
					.successHandler(new CustomLoginSuccessHandler()) //로그인 성공 핸들러 230120한아
					.usernameParameter("email")
					.passwordParameter("password")
					.permitAll()
				)
				.csrf(t -> t.disable())
				.headers().frameOptions().sameOrigin()

		;
		return http.build();
	}
}