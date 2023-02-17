package project.domain.entity;


import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "schedule")
@Entity
@Getter
//230104 안나 생성
//230113 문대현 수정
public class ScheduleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_no", unique = true, nullable = false)
	private long scheduleNo; //일정번호
	
	@Column(name = "schedule_name", nullable = false)
	private String scheduleName; //일정종류

	private String title; //일정상세 내용
	
	@Column(nullable = false)
	private LocalDate startDate; //일정시작날짜
	
	@Column(nullable = false)
	private LocalDate endDate; //일정끝나는날짜
	

	@JoinColumn(name = "employee_no")
	@ManyToOne
	private EmployeesEntity employeeNo; //사원번호
	

}

