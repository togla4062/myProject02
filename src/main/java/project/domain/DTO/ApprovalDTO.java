package project.domain.DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import project.domain.entity.DayOffEntity;
import project.domain.entity.EmployeesEntity;
import project.enums.AuthorizeStatus;

@Data
//230119 재근 생성
public class ApprovalDTO {	
	//결재 승인 DTO	
	
	private String type; //휴가종류

	private long dayOffNo; //휴가번호
	
	private String reason; //휴가사유
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate; //휴가시작일
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate EndDate; //휴가종료일
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate draftDate; //기안일
	
	private long employeeNo; //사원번호
	
	private long useDays; //휴가일수
	
	private AuthorizeStatus approval; //승인여부

	
}
