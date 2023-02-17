package project.domain.DTO;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import project.domain.entity.DailyWorkingHoursEntity;

//230111 내 근태리스트 뿌려주기용 DTO 안나작성
@Data
public class AttendanceMyListDTO {
	
	private LocalDate date; //일자
	
	private String clockIn; //출근시간
	
	private String clockOut; //퇴근시간
	
	private String workingHour; //일 근무 시간
	
	private String designatedWorkingHour; //지정 근무 시간
	
	private String status; //근무상태
		
	public AttendanceMyListDTO(DailyWorkingHoursEntity e) {
		this.date = e.getDate();
		this.clockIn = e.getClockIn().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.designatedWorkingHour = "8:00";
		long workingHourS = Duration.between(e.getClockIn(), e.getClockOut()).toSeconds(); //근무시간 초단위로 계산
		if(e.getClockOut().equals(e.getClockIn())) { //출근시간 = 퇴근시간일 경우(퇴근 버튼을 안눌렀을 때)
			this.workingHour = "-";
			this.clockOut = "-";
		} else { // 퇴근버튼을 눌렀을 때
			this.clockOut = e.getClockOut().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			if(workingHourS < 3600) {
				this.workingHour = "-0시간" + Long.toString(59-(workingHourS % 3600 / 60)) + "분";
			} else {
				this.workingHour = Long.toString(workingHourS / 3600 - 1) + "시간" + Long.toString(workingHourS % 3600 / 60) + "분";
			}
		}
		this.status =e.getStatus();
		//휴가 넣어야함
	}




}