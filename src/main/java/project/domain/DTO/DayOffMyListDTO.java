package project.domain.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import project.domain.entity.DayOffEntity;
import project.domain.entity.DaysOffNumbersEntity;
import project.enums.AuthorizeStatus;

@Data
//230109 재근 생성
public class DayOffMyListDTO {
	//내 휴가 리스트 DTO
	
	private String type; //휴가종류
	
	private String reason; //휴가사유
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate; //휴가시작일
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate EndDate; //휴가종료일
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate draftDate; //기안일
	
	private long useDays; //휴가일수
	
	private AuthorizeStatus approval; //결재상태

	public DayOffMyListDTO(DayOffEntity e) {
		this.type = e.getType();
		this.reason = e.getReason();
		this.startDate = e.getStartDate();
		this.EndDate = e.getEndDate();
		this.draftDate = e.getDraftDate();
		this.useDays = e.getUseDays();
		this.approval = e.getApproval();
	}
	
	
	
}