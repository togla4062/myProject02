package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import project.service.CalendarService;

@Controller
public class CalendarController {

	/* 20230111 문대현 생성 */
	
	@Autowired
	CalendarService calendarService;
	
	//캘린더 페이지로 이동 기능
	@GetMapping("/calendar")
	public String calendar() {
		return "/calendar/calendar";
	}
	
	//Ajax로 캘린더에 출력할 이벤트 데이터들 전송 해 주는 기능
	@ResponseBody
	@PostMapping("/calendar/data")
	public String calendarData(){
		
		return calendarService.findCalendarEventData();
	}
	
}
