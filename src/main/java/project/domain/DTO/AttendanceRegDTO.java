package project.domain.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;


import lombok.Data;
import project.domain.entity.DailyWorkingHoursEntity;

@Data
public class AttendanceRegDTO {
	
	private long workNo;//근태번호
	
	private LocalDate date; //날짜
	
	private String clockIn;
	
	private String clockOut;
	
	private long employeeNo;
	
	private String status;

	public DailyWorkingHoursEntity toDailyWorkingHoursEntity() {
		return DailyWorkingHoursEntity.builder()
				.workNo(workNo)
				.date(date)
				.status("휴가중")
				.build();
	}


}