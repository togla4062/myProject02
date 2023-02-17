package project.domain.DTO;


import lombok.Data;
import project.domain.entity.EmployeesEntity;


//230113 전체 휴가리스트 DTO 재근 작성
//230111 전체 근태리스트 뿌려주기용 DTO 안나작성

@Data
public class DayOffListEmpDTO {

	private long no; //사원번호
	
	private String name; //이름
	
	private String department; //부서
	
	private String position; //직급
	
	public DayOffListEmpDTO(EmployeesEntity e) {
		this.no = e.getNo();
		this.name = e.getName();
		this.department = e.getDepartmentNo().getDepartmentName();
		this.position = e.getPosition().getLabel();		
	}




}