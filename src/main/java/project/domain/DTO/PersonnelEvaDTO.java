package project.domain.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.PersonnelEvaEntity;

@Data
@NoArgsConstructor
public class PersonnelEvaDTO {

	private long no;
	
	private int prepareScore; //업무 준비능력 점수
	
	private int performAbility; //독자적인 수행능력점수
	
	private int anotherAdility; //부수업무 수행력
	
	private int freeTime; //하루 여가시간
	
	private int foodAmount; //하루 식사량
	
	private int totalScore; //총점
	
	private String empGrade;
	
	private long empNo;

	
	public PersonnelEvaEntity saveEntity(EmployeesEntity empNo) {
		return PersonnelEvaEntity.builder()
				.no(empNo.getNo())
				.prepareScore(prepareScore).performAbility(performAbility)
				.anotherAdility(anotherAdility).freeTime(freeTime).empGrade(empGrade)
				.foodAmount(foodAmount).totalScore(totalScore).empNo(empNo.getNo())
				.build();
	}
	
	
	public PersonnelEvaEntity updateEntity(EmployeesEntity empNo) {	
			return PersonnelEvaEntity.builder()
					.no(empNo.getNo()).prepareScore(prepareScore).performAbility(performAbility)
					.anotherAdility(anotherAdility).freeTime(freeTime).empGrade(empGrade)
					.foodAmount(foodAmount).totalScore(totalScore).empNo(empNo.getNo())
					.build();
		
	
	}
	
	public PersonnelEvaDTO(PersonnelEvaEntity perE) {
		this.no=perE.getNo();
		this.prepareScore =perE.getPrepareScore();
		this.performAbility =perE.getPerformAbility();
		this.anotherAdility =perE.getAnotherAdility();
		this.freeTime =perE.getFreeTime();
		this.foodAmount=perE.getFoodAmount();
		this.totalScore=perE.getTotalScore();
		this.empNo=perE.getEmpNo();
	}
}