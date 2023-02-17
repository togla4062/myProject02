package project.domain.DTO;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import project.domain.entity.EmployeesEntity;

@Getter
@Setter
public class EmployeesDetailUpdateDTO {
	/* 230113 한아 작성 */
	
	private String imgUrl; //이미지url
	private long no;//사원번호
	private String departmentName; //부서이름
	private String name;//이름
	private String email;//이메일
	private String position;//직급
	private String phone;//연락처
	private LocalDate joinDate;//입사일
	private LocalDate resignDate;//퇴사일
	private String extension;//내선번호
	private LocalDate birthDate;//생년월일
	private String mainWork;//주 업무
	private long salary;//급여
	private boolean deleteStatus;//삭제여부
	private int editAuthority;//수정권한
	private String yesorno;//수정권한이있나요?
	
	public EmployeesDetailUpdateDTO(EmployeesEntity ent) {
		this.imgUrl = ent.getImageNo().getUrl();
		this.no = ent.getNo();
		this.departmentName = ent.getDepartmentNo().getDepartmentName();
		this.name = ent.getName();
		this.email = ent.getEmail();
		this.position = ent.getPosition().label();
		this.phone = ent.getPhone();
		this.joinDate = ent.getJoinDate();
		this.resignDate = ent.getResignDate()==null ? LocalDate.now() : ent.getResignDate();
		this.extension = ent.getExtension();
		this.birthDate = ent.getBirthDate();
		this.mainWork = ent.getMainWork();
		this.salary = ent.getSalary();
		this.deleteStatus = ent.isDeleteStatus();
		this.editAuthority = ent.getEditAuthority().ordinal();
		if(this.editAuthority==0) {yesorno="N";} else {yesorno="Y";}
		
	}
	
	
	

}
