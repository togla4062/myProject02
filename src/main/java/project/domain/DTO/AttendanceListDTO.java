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
public class AttendanceListDTO {

	private long workNo; //근태번호
	
	private long employeeNo; //사원번호
	
	private String name; //사원이름
	
	private String department; //부서
	
	private String position; //직급
	
	private LocalDate date; //날짜
	
	private String clockIn; //출근시간
	
	private String clockOut; //퇴근시간
	
	private String workingHour; //업무시간
	
	private String designatedWorkingHour; //지정 근무 시간
	
	private String status; //근무상태
	
	public AttendanceListDTO(DailyWorkingHoursEntity e) {
		this.workNo = e.getWorkNo();
		this.employeeNo = e.getEmployee().getNo();
		this.name = e.getEmployee().getName();
		this.department = e.getEmployee().getDepartmentNo().getDepartmentName();
		this.position = e.getEmployee().getPosition().getLabel();
		this.date = LocalDate.from(e.getDate());
		this.clockIn = e.getClockIn().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		this.designatedWorkingHour = "8:00";
		this.status = e.getStatus();
		
		long workingHourS = Duration.between(e.getClockIn(), e.getClockOut()).toSeconds(); //근무시간 초단위로 계산
		if(e.getClockOut().equals(e.getClockIn())) { //출근시간 = 퇴근시간일 경우(퇴근 버튼을 안눌렀을 때)
			this.workingHour = "-"; 
			this.clockOut = "-";
		} else { // 퇴근버튼을 눌렀을 때
			this.clockOut = e.getClockOut().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
			if(workingHourS < 3600) {
				this.workingHour = "-0시간" + Long.toString(59-(workingHourS % 3600 / 60)) + "분" + Long.toString(59-(workingHourS % 60)) + "초";
			} else {
				this.workingHour = Long.toString(workingHourS / 3600 - 1) + "시간" + Long.toString(workingHourS % 3600 / 60) + "분" + Long.toString(workingHourS % 60) + "초";
			}
		}
		

	}
}