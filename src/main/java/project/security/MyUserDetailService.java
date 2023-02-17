package project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import project.domain.repository.EmployeesEntityRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	/* 20230109 문대현 수정 */
	
	// DB 테이블에서 인증처리하기 위한 메서드
	@Autowired
	private EmployeesEntityRepository emRepo;

	public MyUserDetailService(EmployeesEntityRepository employeesEntityRepository) {
	this.emRepo = employeesEntityRepository;	
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new MyUserDetails(emRepo.findByEmail(username));
	}

}