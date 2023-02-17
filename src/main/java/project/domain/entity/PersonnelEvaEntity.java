package project.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "personnel_eva")
@Entity
@Getter
public class PersonnelEvaEntity {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personnal_no" ,nullable = false, unique = true) 
	private long no; //인사평가 기본넘버 pk 
	
	@Column(name="prepare_score",nullable = false)
	private int prepareScore; //업무 준비능력 점수
	
	@Column(name="perform_ability",nullable = false)
	private int performAbility; //독자적인 수행능력점수
	
	@Column(name="another_ability", nullable = false)
	private int anotherAdility; //부수업무 수행력
	
	@Column(name="free_time", nullable = false)
	private int freeTime; //하루 여가시간
	
	@Column(name ="food_amount", nullable = false)
	private int foodAmount; //하루 식사량
	
	@Column(name ="total_score" ,nullable = false)
	private int totalScore; //총점
	
	@Column(name="emp_grade" )
	private String empGrade;
	
	
	//@ManyToOne
	@Column(name = "regist_no", nullable = false)
	private long empNo; 
	
}