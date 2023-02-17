package project.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler{
	/* 230120 한아 작성 */

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println(authentication.getAuthorities());
		String authority = authentication.getAuthorities().toString();
		
		if(authority.contains("RESIGNED")) {
			response.sendRedirect("/resigned");
			return;
		}
		
		response.sendRedirect("/");
	}
	
	

}
