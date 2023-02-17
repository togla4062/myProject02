package project.domain.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;
import project.domain.entity.DailyWorkingHoursEntity;

//출근 입력용 DTO 230111 안나
@Data
public class AttendanceRegClockInDTO {
	
	private long workNo;//근태번호
	
	private LocalDate date; //날짜
	
	private LocalTime clockIn = LocalTime.now(); //출근시간
	
	private String status = "근무중";

	public DailyWorkingHoursEntity clockInToDailyWorkingHoursEntity() {
		return DailyWorkingHoursEntity.builder()
				.workNo(workNo)
				.date(date)
				.clockIn(clockIn)
				.status(status)
				.build();
	}


}