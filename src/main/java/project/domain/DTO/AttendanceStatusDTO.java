package project.domain.DTO;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.domain.entity.DailyWorkingHoursEntity;
import project.domain.entity.DayOffEntity;
import project.domain.entity.EmployeesEntity;

//230111 전체 근태리스트 뿌려주기용 DTO 안나작성
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceStatusDTO {
	
	private String status; //근무상태
	
	public AttendanceStatusDTO(DailyWorkingHoursEntity e) {
		
		this.status = "휴가중";
		
		

	}
}