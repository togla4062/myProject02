package project.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "days_off_numbers")
@Entity
@Getter
//230104 안나 생성
public class DaysOffNumbersEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dno", unique = true, nullable = false)
	private long dno;
	
	@JoinColumn(name = "no", nullable = false)
	@ManyToOne
	private EmployeesEntity no; //사원번호
	
	@Column(name = "total_days")
	private long totalDays; //총 휴가일수
	
	@Column(name = "use_days")
	private long useDays; //사용일수
	
	//public DaysOffNumbersEntity updateUseDays() {
	//	this.useDays += useDays;
	//	return this;
	//}

}

