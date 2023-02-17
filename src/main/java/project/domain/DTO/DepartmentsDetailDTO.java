package project.domain.DTO;

import lombok.Data;
import project.domain.entity.DepartmentsEntity;

@Data
public class DepartmentsDetailDTO {
	/* 230116 한아 작성 */
	
	private long departmentNo; //부서번호
	private String departmentName;//부서명
	private String departmentHead; //부서장
	
	
	public DepartmentsEntity toDepartment() {
		
		return DepartmentsEntity.builder()
				.departmentName(departmentName)
				.departmentHead(departmentHead)
				.build();
		
	}
	
	public DepartmentsDetailDTO(DepartmentsEntity ent) { //.map(DepartmentsDTO::new)에 필요
		this.departmentNo = ent.getDepartmentNo();
		this.departmentName = ent.getDepartmentName();
		this.departmentHead = ent.getDepartmentHead();
	}
	
	

}
