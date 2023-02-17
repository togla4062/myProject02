package project.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import project.domain.DTO.AttendanceRegClockInDTO;
import project.domain.DTO.AttendanceRegClockOutDTO;
import project.domain.DTO.AttendanceRegDTO;
import project.security.MyUserDetails;
import project.service.AttendanceService;

@Controller
public class AttendanceController {

	@Autowired
	private AttendanceService service;

	// 출근데이터 전송 230111 작성 안나
	@ResponseBody
	@PostMapping("/attendance/in")
	public String attendanceIn(@AuthenticationPrincipal MyUserDetails myUserDetails, AttendanceRegClockInDTO attendanceRegInDTO) {
		service.saveAttIn(myUserDetails.getNo(), attendanceRegInDTO);
		return "attinSuccess";
	}

	// 퇴근데이터 전송 230111 작성 안나 : logController
	@ResponseBody
	@PatchMapping("/attendance/out")
	public String attendanceOut(@AuthenticationPrincipal MyUserDetails myUserDetails,
			AttendanceRegClockOutDTO attendanceRegOutDTO) {
		service.saveAttOut(myUserDetails.getNo(), attendanceRegOutDTO);
		return "attoutSuccess";
	}
	// 230104 한아 작성 근태 리스트
	// 전체 근태리스트 뿌려주기 230111 수정 안나
	@GetMapping("/attendanceList")//attendanceList
	public String attendenceList(String keyword, Model model, @PageableDefault(size = 10)Pageable pageable) {
		service.listAtt(model, pageable, keyword);
		return "AttendanceMgmt/attendanceList";
	}
	
	//전체 근태리스트 뿌려주기 - 부서별 검색
	//@ResponseBody 페이지 이동하기 위해서는 이거 쓰면 안됨
	@GetMapping("/attendanceList/{department}")
	public String attendenceListByDepartmentNo(Model model, @PathVariable Long department, @PageableDefault(size = 10)Pageable pageable) {
		service.findAllByDepartmentNo(model, department, pageable);
		return "AttendanceMgmt/attendanceListGroupByDepartment";
	}
	
	//직원별 근태+휴가 뿌려주기 230111 수정 안나 - 휴가 미설정 230117 페이징 추가 안나
	@GetMapping("/personalAttendance/{no}")
	public String personalAttendance(@PathVariable long no, Model model, @PageableDefault(size = 10)Pageable pageable) {
		service.personalAtt(no, model, pageable);
		return "AttendanceMgmt/personalAttendance";
	}
	
	//직원별 근태+휴가 뿌려주기 + 230119 날짜별 검색 안나
	@GetMapping("/personalAttendance/dateSearch/{no}")
	public String personalAttendanceDateSearch(@PathVariable long no, Model model, @PageableDefault(size = 10)Pageable pageable, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateStart, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateEnd) {
		service.personalAttSearch(no, model, pageable, dateStart, dateEnd);
		return "AttendanceMgmt/personalAttendance";
	}

	//직원별 근태 뿌려주기 230111 수정 안나 230117 페이징 추가 안나
	@GetMapping("/personalWorkingDay/{no}")
	public String personalWorkingDay(@PathVariable long no, Model model, @PageableDefault(size = 10)Pageable pageable) {
		service.personalAtt(no, model, pageable);
		return "AttendanceMgmt/personalWorkingDay";
	}
	
	//직원별 근태 뿌려주기 + 230119 날짜별 검색 안나
	@GetMapping("/personalWorkingDay/dateSearch/{no}")
	public String personalWorkingDayDateSearch(@PathVariable long no, Model model, @PageableDefault(size = 10)Pageable pageable, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateStart, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateEnd) {
		service.personalAttSearch(no, model, pageable, dateStart, dateEnd);
		return "AttendanceMgmt/personalWorkingDay";
	}
	
	//내 근태+휴가 뿌려주기 230111 수정 안나 - 휴가 미설정 230117 페이징 추가 안나
	@GetMapping("/myAttendance")
	public String myAttendance(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model, @PageableDefault(size = 10)Pageable pageable) {
		service.myListAtt(myUserDetails.getNo(), model, pageable);
		return "AttendanceMgmt/myAttendance";
		
	}
	
	//내 근태+휴가 뿌려주기 + 230120 날짜별 검색 안나
	@GetMapping("/myAttendance/dateSearch")
	public String myAttendanceDateSearch(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model, @PageableDefault(size = 10)Pageable pageable, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateStart, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateEnd) {
		service.myListAttDaySearch(myUserDetails.getNo(), model, pageable, dateStart, dateEnd);
		return "AttendanceMgmt/myAttendance";
	}

	//내 근태만 뿌려주기 230111 수정 안나 230117 페이징 추가 안나
	@GetMapping("/myWorkingDay")
	public String myWorkingDay(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model, @PageableDefault(size = 10)Pageable pageable) {
		service.myListAtt(myUserDetails.getNo(), model, pageable);
		return "AttendanceMgmt/myWorkingDay";
	}
	
	//내 근태만 뿌려주기 + 230120 날짜별 검색 안나
	@GetMapping("/myWorkingDay/dateSearch")
	public String myWorkingDayDateSearch(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model, @PageableDefault(size = 10)Pageable pageable, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateStart, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate dateEnd) {
		service.myListAttDaySearch(myUserDetails.getNo(), model, pageable, dateStart, dateEnd); //근태+휴가와 동일 서비스
		return "AttendanceMgmt/myWorkingDay";
	}
	
	
}
