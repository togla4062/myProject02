package project.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.domain.DTO.DepartmentsUpdateDTO;

@SuppressWarnings("serial")
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
@Entity
@Getter
//230104 안나 생성
public class DepartmentsEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_no", unique = true, nullable = false)
	private long departmentNo; //부서번호
	
	@Column(name = "department_name", nullable = false)
	private String departmentName;//부서명
	
	@Column(name = "department_head", nullable = true) //한아 수정 nullable true
	private String departmentHead; //부서장

	//230119 한아 수정
	//부서 정보 update, 부서장 없으면 '미정'
	public DepartmentsEntity updateDepartment(DepartmentsUpdateDTO dto) {
		this.departmentName = dto.getDepartmentName();
		if(dto.getDepartmentHead()!="")
			this.departmentHead = dto.getDepartmentHead();
		else this.departmentHead = "미정";
		return null;
	}

	//부서장 퇴직처리 되면 해당부서 부서장 '미정'
	public DepartmentsEntity updateDepartmentHead(DepartmentsUpdateDTO udto) {
		this.departmentHead = "미정";
		return null;
	}
	

}