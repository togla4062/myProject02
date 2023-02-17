package project.domain.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;
import project.domain.entity.DailyWorkingHoursEntity;

//퇴근 입력용 DTO 230111 안나
@Data
public class AttendanceRegClockOutDTO {
	
	private long workNo;//근태번호
	
	private LocalDate date; //날짜
	
	private LocalTime clockIn; //출근시간

	private LocalTime clockOut; //퇴근시간
	
	private String status = "근무종료";
	
	private long employeeNo; //사원번호
	
	public DailyWorkingHoursEntity clockOutToDailyWorkingHoursEntity() {
		return DailyWorkingHoursEntity.builder()
				.workNo(workNo)
				.date(date)
				.clockOut(clockOut)
				.status(status)
				.build();
	}


}