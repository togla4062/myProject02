package project.domain.DTO;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ScheduleEntity;

@Data
public class ScheduleUpdateDTO {

	private long scheduleNo; //일정 번호
	
	private String scheduleName; // 일정 종류
	
	private String title; // 일정 상세 내용
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate; // 시작 일자
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate; // 종료 일자

	/** 일정 업데이트용 편의 메서드
	 * 
	 * @param emp 로그인한 사원 정보
	 * @return ScheduleEntity 타입으로 반환
	 */
	public ScheduleEntity toEntityForUpdate(EmployeesEntity emp) {
		return ScheduleEntity.builder().scheduleNo(scheduleNo).scheduleName(scheduleName).title(title).startDate(startDate).endDate(endDate).employeeNo(emp).build();
	}
}
