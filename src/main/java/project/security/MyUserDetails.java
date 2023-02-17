package project.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import project.domain.entity.DepartmentsEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ImagesEntity;
import project.enums.DepartmentRank;
import project.enums.MyRole;

@Getter
public class MyUserDetails extends User{

	/* 20230110 문대현 수정 */
	
	private long no; //사원번호
	private String email; //이메일주소
	private String name; //이름
	private DepartmentsEntity departmentNo; //부서명
	private DepartmentRank position; //직급
	private ImagesEntity imageNo; //사원 이미지
	private MyRole editAuthority;//수정권한
	
	//extends User
	public MyUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public MyUserDetails(EmployeesEntity entity) {
		this(entity.getEmail(), entity.getPassword(), entity.getRoles()//Set<MyRole>  --> Set<GrantedAuthority>
			.stream()
			.map(myRole->new SimpleGrantedAuthority(myRole.getRole()))
			.collect(Collectors.toSet()));
		this.no = entity.getNo();
		this.email = entity.getEmail();
		this.name = entity.getName();
		this.departmentNo = entity.getDepartmentNo();
		this.position = entity.getPosition();
		this.imageNo = entity.getImageNo();
		this.editAuthority = entity.getEditAuthority();
	}
	
	
	
	

}