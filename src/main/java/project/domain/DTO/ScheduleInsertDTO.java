package project.domain.DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ScheduleEntity;

@Data
public class ScheduleInsertDTO {

	private String select; // 일정 종류
	private String title; // 일정 상세 내용
	private String start; // 시작 일자
	private String end; // 종료 일자
	
	/** ScheduleEntity save 용 편의 메서드
	 * 
	 * @param emp 로그인한 회원 정보
	 * @return ScheduleEntity 값
	 */
	public ScheduleEntity toSceduleEntityForSave(EmployeesEntity emp) {
		
		//입력받은 데이터 날짜 데이터로 변환
		LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);
		
		return ScheduleEntity.builder().scheduleName(select).title(title).startDate(startDate).endDate(endDate).employeeNo(emp).build();
	}
}
