package project.service.proc;

import java.time.LocalDate;


import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.domain.entity.BoardCNCEntity;
import project.domain.entity.DayOffEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ScheduleEntity;
import project.enums.AuthorizeStatus;
import project.service.CNCBoardService;
import project.service.CalendarService;
import project.service.DayOffService;
import project.service.EmployeesService;
import project.service.ScheduleService;

@Service
public class CalendarServiceProc implements CalendarService {

	/* 20230111 문대현 생성 */
	// 전송해야 할 날짜 형식 yyyy-MM-dd 자리 수도 맞춰야함(String 타입)
	// 시간도 명확하게 표현 하고 싶을 시 yyyy-MM-ddThh:mm:ss 형식으로 해야함 ([ex] 2016-05-06T10:00:00)
	/*
	 * 데이터 저장 양식
	 * 
	 * "title" : "캘린더에 띄울 글자 데이터" "color" : "#FF0000" 배경색 변경(색상코드) "textColor" :
	 * "#FFFF00" 글자색 변경(색상코드) "start" : "yyyy-MM-dd" 이벤트 시작 날짜 "end" : "yyyy-MM-dd"
	 * 이벤트 끝나는 날짜(중간날짜들은 자동으로 적용됨)
	 * 
	 */

	@Autowired
	EmployeesService employeesService;

	@Autowired
	CNCBoardService cncBoardService;

	@Autowired
	ScheduleService scheduleService;
	
	@Autowired
	DayOffService dayOffService;

	// 캘린더에 출력할 이벤트 데이터를 처리하는 기능
	@Override
	public String findCalendarEventData() {
		
		JSONArray jsonArr = new JSONArray();

		// 사원 정보중 생일 날짜를 조회해서 ajax로 전송해 주는 기능
		List<EmployeesEntity> empList = employeesService.findAll();

		for (int i = 0; i < empList.size(); i++) {
			LocalDate now = LocalDate.now();
			int year = now.getYear();
			HashMap<String, Object> hash = new HashMap<>();
			JSONObject jsonObj = new JSONObject();

			String date = DateTimeFormatter.ofPattern("MM-dd").format(empList.get(i).getBirthDate());
			hash.put("start", year + "-" + date);
			hash.put("title", empList.get(i).getName() + " 생일날");
			hash.put("color", "#FFD700");
			hash.put("textColor", "#191970");

			jsonObj = new JSONObject(hash);
			jsonArr.put(jsonObj);
		}

		// 사원 경조사 날짜 정보 조회해서 ajax로 전송해 주는 기능
		List<BoardCNCEntity> cncList = cncBoardService.findAll();

		for (int i = 0; i < cncList.size(); i++) {
			LocalDate now = LocalDate.now();
			int year = now.getYear();
			HashMap<String, Object> hash = new HashMap<>();
			JSONObject jsonObj = new JSONObject();

			String date = DateTimeFormatter.ofPattern("MM-dd").format(cncList.get(i).getEventDate());
			hash.put("start", year + "-" + date);
			hash.put("title", cncList.get(i).getRegistNo().getName() + "(님)의 경조사 날");
			hash.put("color", "#C0C0C0");
			hash.put("textColor", "#FFFFFF");

			jsonObj = new JSONObject(hash);
			jsonArr.put(jsonObj);
		}

		// 사원 휴가 정보 조회해서 ajax로 전송해 주는 기능 넣을 곳
		List<DayOffEntity> dayoffList = dayOffService.findbyApproval(AuthorizeStatus.Approval);
		
		for (int i = 0; i < dayoffList.size(); i++) {
			HashMap<String, Object> hash = new HashMap<>();
			JSONObject jsonObj = new JSONObject();

			hash.put("start", dayoffList.get(i).getStartDate());
			hash.put("end", dayoffList.get(i).getEndDate().plusDays(1));
			hash.put("title",dayoffList.get(i).getType() + "-" + dayoffList.get(i).getEmployeeNo().getName());
			hash.put("color", "#FF8C00");
			hash.put("textColor", "#FFFFFF");

			jsonObj = new JSONObject(hash);
			jsonArr.put(jsonObj);
		}
		// 사원 일정 정보 조회해서 ajax로 전송해 주는 기능
		List<ScheduleEntity> scheduleList = scheduleService.findAll();
		for (int i = 0; i < scheduleList.size(); i++) {
			HashMap<String, Object> hash = new HashMap<>();
			JSONObject jsonObj = new JSONObject();

			hash.put("start", scheduleList.get(i).getStartDate());
			hash.put("end", scheduleList.get(i).getEndDate().plusDays(1));
			hash.put("title",scheduleList.get(i).getScheduleName() + "-" + scheduleList.get(i).getEmployeeNo().getName());
			if(scheduleList.get(i).getScheduleName().equals("출장")) {
				hash.put("color", "#1E90FF");
				hash.put("textColor", "#FFFFFF");
			}else if(scheduleList.get(i).getScheduleName().equals("야근")) {
				hash.put("color", "#1A252F");
				hash.put("textColor", "#FFFFFF");
			}
			else if(scheduleList.get(i).getScheduleName().equals("미팅")) {
				hash.put("color", "#23A100");
				hash.put("textColor", "#FFFFFF");
			}
			else if(scheduleList.get(i).getScheduleName().equals("회의")) {
				hash.put("color", "#708090");
				hash.put("textColor", "#FFFFFF");
			}
			else {
				hash.put("color", "#97C6F0");
				hash.put("textColor", "#FFFFFF");
			}

			jsonObj = new JSONObject(hash);
			jsonArr.put(jsonObj);
		}

		// ajax에 전송 준비
		String result = jsonArr.toString();

		return result;
	}

}
