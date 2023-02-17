package project.domain.DTO;

import java.text.DecimalFormat;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.domain.entity.EmployeesEntity;

@Getter
@Setter
@ToString
public class EmployeesDetailDTO {
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
	private String salary;//급여
	private boolean deleteStatus;//삭제여부
	private int editAuthority;//수정권한
	private String yesorno;//수정권한이있나요?
	private String empGrade; //인사평가 등급
	
	public String phone_format(String number) { //detail에서 보여질때 formatting
	      String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
	      return number.replaceAll(regEx, "$1-$2-$3");
	}
	public DecimalFormat sformatter = new DecimalFormat("###,###");

	public EmployeesDetailDTO(EmployeesEntity ent) {
		this.imgUrl = ent.getImageNo().getUrl();
		this.no = ent.getNo();
		this.departmentName = ent.getDepartmentNo().getDepartmentName();
		this.name = ent.getName();
		this.email = ent.getEmail();
		this.position = ent.getPosition().label();
		this.phone = phone_format(ent.getPhone());
		this.joinDate = ent.getJoinDate();
		this.resignDate = ent.getResignDate()==null ? LocalDate.now() : ent.getResignDate();
		this.extension = ent.getExtension();
		this.birthDate = ent.getBirthDate();
		this.mainWork = ent.getMainWork();
		this.salary = sformatter.format(ent.getSalary());
		this.deleteStatus = ent.isDeleteStatus();
		this.editAuthority = ent.getEditAuthority().ordinal();
		if(this.editAuthority==1||this.editAuthority==3) {yesorno="Y";} else {yesorno="N";}
		//this.empGrade = ent.getEmpGrade();

	}
	
	
	

}