package project.domain.DTO;

import lombok.Data;
import project.domain.entity.EmployeesEntity;
import project.enums.DepartmentRank;
import project.enums.MyRole;

@Data
public class EmployeesUpdateDTO {
	
	private MyRole edit_authority;
	
	private long departmentNo; //부서번호
	private String name;//이름
	private String email;//이메일
	private DepartmentRank position;//직급
	private String phone;//연락처
	private String joinDate;//입사일
	private String resignDate;//퇴사일
	private String extension;//내선번호
	private String birthDate;//생년월일
	private String mainWork;//주 업무
	private long salary;//급여
	private String empGrade; //인사평가 등급
	

	public EmployeesEntity addEmpGrade(long no, String grade) {
		return EmployeesEntity.builder()
				.no(no)
				.empGrade(grade)
				.build();
	}

	
}