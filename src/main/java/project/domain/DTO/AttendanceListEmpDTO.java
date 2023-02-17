package project.domain.DTO;


import lombok.Data;
import project.domain.entity.DailyWorkingHoursEntity;
import project.domain.entity.EmployeesEntity;

//230111 전체 근태리스트 뿌려주기용 DTO 안나작성
@Data
public class AttendanceListEmpDTO {

	private long no; //사원번호
	
	private String name; //이름
	
	private String department; //부서
	
	private String position; //직급
	
	public AttendanceListEmpDTO(EmployeesEntity e) {
		this.no = e.getNo();
		this.name = e.getName();
		this.department = e.getDepartmentNo().getDepartmentName();
		this.position = e.getPosition().getLabel();		
	}




}