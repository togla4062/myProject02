package project.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.domain.DTO.DayOffAppDTO;
import project.domain.DTO.DayOffInsertDTO;
import project.domain.repository.DayOffEntityRepository;
import project.enums.AuthorizeStatus;
import project.security.MyUserDetails;
import project.service.AttendanceService;
import project.service.DayOffService;

@Controller
public class ApprovalController {
	
	@Autowired
	private DayOffService service;
	
	@Autowired
	private AttendanceService aService;
	
	@Autowired
	DayOffEntityRepository dayOffRepo;
	
	//휴가신청 페이지
	@GetMapping("/dayoff")
    public String dayoff() {
        return "approvalMgmt/dayOff";
    }
	
	//휴가신청 save
	@PostMapping("/dayoff")
	public String dayOff(DayOffInsertDTO dto, HttpServletResponse response) {
		service.save(dto, response); //휴가등록
		service.update(dto); //휴가일수 업데이트
		return "redirect:/myDayOff";
	}
	
	//직원별 휴가리스트
	@GetMapping("/personalDayOff/{no}")
    public String personalDayOff(@PathVariable long no, Model model) {
		service.personalDayOff(no, model); //no :  day off no
        return "AttendanceMgmt/personalDayOff";
    }
	
	//내 휴가리스트
	@GetMapping("/myDayOff")
	public String myDayOff(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
		service.mydayoff(myUserDetails.getNo(), model);
	    return "AttendanceMgmt/myDayOff";
	}
	
	//부서장 결재리스트
	@GetMapping("/approvalList")
    public String approvalList(@AuthenticationPrincipal MyUserDetails myUserDetails, 
    		@RequestParam(value="pageNum", required = false, defaultValue="1")int pageNum, 
    		@RequestParam(value="search", required = false) String search,
    		@RequestParam(value="searchType", required = false) String searchType, Model model) {
		service.appList(myUserDetails.getDepartmentNo(), pageNum, search, searchType, model);
        return "approvalMgmt/approvalList";
    }
	
	//대표 결재리스트
	@GetMapping("/approvalList2")
    public String approvalList2(
    		@RequestParam(value="pageNum", required = false, defaultValue="1") int pageNum, 
    		@RequestParam(value="search", required = false) String search,
    		@RequestParam(value="searchType", required = false) String searchType, Model model) {
		service.approvalList2(pageNum, search, searchType, model);
        return "approvalMgmt/approvalList2";
    }
	
	//부서장 결재 디테일
	@GetMapping("/dayoffApp")
	public String dayOffApp(@RequestParam long dayOffNo, Model model) {
		service.detail(dayOffNo, model); //no :  day off no
		return "approvalMgmt/dayOffApp";
	}
	
	//대표 결재 디테일
	@GetMapping("/dayoffApp2")
	public String dayOffApp2(@RequestParam long dayOffNo, Model model) {
		service.detail2(dayOffNo, model); //no :  day off no
		return "approvalMgmt/dayOffApp2";
	}
	//부서장 결재승인
	@Transactional
	@GetMapping("/approval/{dayOffNo}")
	public String approval(@PathVariable long dayOffNo, DayOffAppDTO dto) {
		//System.out.println(dayOffNo);
		//dayOffRepo.findById(dayOffNo).get().addApproval(AuthorizeStatus.FirstApproval);
		dayOffRepo.findById(dayOffNo).map(t -> t.firstApproval(dto));
		return "redirect:/approvalList";
	}
	
	//대표 결재승인
	@Transactional
	@GetMapping("/approval2/{dayOffNo}")
	public String approval2(@PathVariable long dayOffNo, DayOffAppDTO dto) {
		dayOffRepo.findById(dayOffNo).map(t -> t.finalApproval(dto));
//		LocalDate startDate = dto.getStartDate();
//		LocalDate endDate = dto.getEndDate();
//		System.out.println("startDate: " + startDate);
//		System.out.println("endDate: " + endDate);
		aService.saveDayOff(dayOffNo, dto);
		return "redirect:/approvalList2";
	}
	
//	@PostMapping("/approvalDelete")
//	public String approvalDelete(long dayOffNo) {
//		service.delete(dayOffNo);
//		return "redirect:/approvalList";
//	}
	
	//부서장 결재 반려
	@Transactional
	@PostMapping("/approvalReturn/{dayOffNo}")
	public String approvalReturn(@PathVariable long dayOffNo, DayOffAppDTO dto) {
		dayOffRepo.findById(dayOffNo).map(t -> t.returnApproval(dto));
		return "redirect:/approvalList";
	}
	
//	@PostMapping("/approvalDelete")
//	public String approvalDelete(long dayOffNo) {
//		service.delete(dayOffNo);
//		return "redirect:/approvalList";
//	}

	//대표 결재 반려
	@Transactional
	@PostMapping("/approvalReturn2/{dayOffNo}")
	public String approvalReturn2(@PathVariable long dayOffNo, DayOffAppDTO dto) {
		dayOffRepo.findById(dayOffNo).map(t -> t.returnApproval(dto));
		return "redirect:/approvalList2";
	}
	
}

