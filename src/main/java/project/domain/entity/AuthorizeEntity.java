package project.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import project.enums.AuthorizeStatus;
import project.enums.DepartmentRank;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authorize")
@Entity
@Getter
@DynamicUpdate
//230104 안나 생성
public class AuthorizeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorize_no", unique = true, nullable = false)
	private long authorizeNo; //결재번호
	
	@Column(name = "authorize_status")
	@Enumerated(EnumType.STRING)//1:N 즉시로딩
	private AuthorizeStatus authorizeStatus;//결재상태
	
	@Column(name = "authorize_date")
	private LocalDateTime authorizeDate; //결재일자
	
	@JoinColumn(name = "employee_no")
	@ManyToOne
	private EmployeesEntity employeeNo; //사원번호
	
	@JoinColumn(name = "day_off_no")
	@ManyToOne
	private DayOffEntity dayOffNo; //휴가번호


}

