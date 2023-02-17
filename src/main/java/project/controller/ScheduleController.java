package project.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.domain.DTO.ScheduleInsertDTO;
import project.domain.DTO.ScheduleUpdateDTO;
import project.security.MyUserDetails;
import project.service.ScheduleService;

@Controller
public class ScheduleController {

	@Autowired
	ScheduleService sceduleService;
	
	//일정관리 페이지로 이동
	@GetMapping("/scheduleList")
	public String scheduleList(String clickedDate, Model model,@AuthenticationPrincipal MyUserDetails myUserDetails) {

		LocalDate date = null;
		
		//로그인한 사원 정보
		long empNo = myUserDetails.getNo();
		
		//인덱스의 캘린더에서 날짜 데이터를 눌러서 해당 페이지로 온 경우
		if(clickedDate == null) {
			date = LocalDate.now();
		}
		//인덱스의 캘린더가 아닌 직접 일정관리 페이지로 온 경우
		else {
			date = LocalDate.parse(clickedDate);
		}
		sceduleService.findAllByToday(date, model, empNo);
		return "schedule/schedule";
	}
	
	// 일정 삭제 후 일정관리 페이지로 리다이렉트 하는 기능
	@PostMapping("/scheduleDelete")
	public String scheduleDelete(String date, long scheduleNo, RedirectAttributes redirectAttributes) {
		
		sceduleService.delete(scheduleNo);

		redirectAttributes.addAttribute("clickedDate", date);
		return "redirect:/scheduleList";
	}
	// 일정 저장 기능(AJax)
	@ResponseBody
	@PostMapping("/scheduleAdd")
	public void scheduleAdd(ScheduleInsertDTO dto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		
		//로그인한 사원 정보
		long empNo = myUserDetails.getNo();
		
		sceduleService.save(dto,empNo);
		
	}
	// 일정 저장 기능
	@PostMapping("/schedulePlus")
	public String schedulePlus(ScheduleInsertDTO dto, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		
		//로그인한 사원 정보
		long empNo = myUserDetails.getNo();
		
		sceduleService.save(dto,empNo);
		
		return "redirect:/scheduleList";
	}
	// 일정 수정 기능
	@PostMapping("/scheduleUpdate")
	public String scheduleUpdate(ScheduleUpdateDTO dto,String date,@AuthenticationPrincipal MyUserDetails myUserDetails, RedirectAttributes redirectAttributes) {
		
		//로그인한 사원 정보
		long empNo = myUserDetails.getNo();
		
		sceduleService.update(dto,empNo);
		redirectAttributes.addAttribute("clickedDate", date);
		return "redirect:/scheduleList";
	}
}
