package project.service.proc;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import project.domain.DTO.DayOffAppDTO;
import project.domain.DTO.DayOffInsertDTO;
import project.domain.DTO.DayOffListDTO;
import project.domain.DTO.DayOffListEmpDTO;
import project.domain.DTO.DayOffMyListDTO;

import project.domain.entity.BoardCNCEntity;

import project.domain.entity.BoardSuggestionsEntity;
import project.domain.entity.DayOffEntity;
import project.domain.entity.DaysOffNumbersEntity;
import project.domain.entity.DepartmentsEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ReplySuggestionsEntity;
import project.domain.repository.DayOffEntityRepository;
import project.domain.repository.DaysOffNumbersEntityRepository;
import project.domain.repository.DepartmentsEntityRepository;
import project.domain.repository.EmployeesEntityRepository;
import project.enums.AuthorizeStatus;

import project.enums.DepartmentRank;
import project.security.MyUserDetails;

import project.service.DayOffService;

@Service
public class DayOffServiceProcess implements DayOffService {

	@Autowired
	private DayOffEntityRepository dayOffRepo;

	@Autowired
	private EmployeesEntityRepository employeesRepo;

	@Autowired
	private DaysOffNumbersEntityRepository daysOffNumbersRepo;

	// 휴가 등록
	@Override
	public void save(DayOffInsertDTO dto, HttpServletResponse response) {

		EmployeesEntity emp = employeesRepo.findById(dto.getEmployeeNo()).orElseThrow();

		DaysOffNumbersEntity numbers = daysOffNumbersRepo.findByNo(emp);
		if (numbers == null) {
			numbers = daysOffNumbersRepo
					.save(DaysOffNumbersEntity.builder().no(emp).totalDays(15).useDays(dto.getUseDays()).build());
		}
		// 신청시 휴가시작일이 이미 등록되있는 휴가날짜사이에 들어있는지 체크해서 없을경우 저장
		boolean check = true;
		List<DayOffEntity> list = dayOffRepo.findByEmployeeNo(emp);
		for (DayOffEntity dayOffEntity : list) {
			List<LocalDate> days = getDatesBetweenTwoDates(dayOffEntity.getStartDate(), dayOffEntity.getEndDate());
			for (LocalDate localDate : days) {
				if (localDate.isEqual(dto.getStartDate())) {
					check = false;
				}
			}
			if (dayOffEntity.getEndDate().isEqual(dto.getStartDate())) {
				check = false;
			}
		}
		if (dto.getUseDays() == null) {
			alertAndBack(response, "신청일수가 없습니다. 날짜를 다시 선택하세요");
		}
		if (check) {
			if (emp.getPosition() == DepartmentRank.DepartmentManager) {
				dayOffRepo.save(dto.toDayOffEntity(emp, AuthorizeStatus.FirstApproval));
			} else if (emp.getPosition() == DepartmentRank.CEO) {
				dayOffRepo.save(dto.toDayOffEntity(emp, AuthorizeStatus.Approval));
			} else {
				dayOffRepo.save(dto.toDayOffEntity(emp, AuthorizeStatus.UnderApproval));
			}
		} else {
			String err404Meassge = "해당 날짜는 이미 존재합니다. 날짜를 다시 선택하세요";
			alertAndBack(response, err404Meassge);
		}

	}

	private static void alertAndBack(HttpServletResponse response, String msg) {
		try {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter w = response.getWriter();
			w.write("<script>alert('" + msg + "');history.go(-1);</script>");
			w.flush();
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 두 날짜 사이의 모든 날짜를 받아오는 기능(시작날짜부터 종료일 전날까지 리스트로 받아짐)
	 * 
	 * @param startDate 시작일(LoclaDate 타입)
	 * @param endDate   종료일(LoclaDate 타입)
	 * @return 두 날짜 사이의 모든 날짜 리스트를 반환
	 */
	public List<LocalDate> getDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
		return startDate.datesUntil(endDate).collect(Collectors.toList());
	}

	// 휴가 신청일수 업데이트
	@Override
	public void update(DayOffInsertDTO dto) {
		// findAllByEmployeeNo(dto.getEmployeeNo());
		EmployeesEntity emp = employeesRepo.findById(dto.getEmployeeNo()).orElseThrow();
		List<DayOffEntity> day = dayOffRepo.findByEmployeeNo(emp);
		DaysOffNumbersEntity use = daysOffNumbersRepo.findByNo(emp);
		long tot = 0;
		for (DayOffEntity dayOffEntity : day) {
			tot += dayOffEntity.getUseDays();
		}

		daysOffNumbersRepo
				.save(DaysOffNumbersEntity.builder().dno(use.getDno()).useDays(tot).totalDays(15).no(emp).build());
	}

	// 사원별 휴가 리스트
	@Override
	public void personalDayOff(long no, Model model) {
		// DayOffEntity detail = dayOffRepo.findAllByDayOffNo(no);
		// model.addAttribute("result", detail);
		model.addAttribute("dayOffListEmp",
				employeesRepo.findById(no).stream().map(DayOffListEmpDTO::new).collect(Collectors.toList()));
		model.addAttribute("dayOffList", dayOffRepo.findByEmployeeNo(employeesRepo.findById(no).orElseThrow()).stream()
				.map(DayOffListDTO::new).collect(Collectors.toList()));
	}

	// 내 휴가 리스트
	@Override
	public void mydayoff(long no, Model model) {
		model.addAttribute("myDayOffList",
				dayOffRepo.findByEmployeeNoOrderByStartDate(employeesRepo.findById(no).orElseThrow()).stream()
						.map(DayOffMyListDTO::new).collect(Collectors.toList()));
	}

	// 부서장 결재 디테일
	@Override
	public void detail(long dayOffNo, Model model) {
		DayOffEntity ent = dayOffRepo.findById(dayOffNo).orElseThrow();
		model.addAttribute("dayOffDetail", ent);
		model.addAttribute("detailEmp", ent.getEmployeeNo());
	}

	// 부서장 결재리스트
	@Override
	public void appList(DepartmentsEntity departmentNo, int pageNum, String search, String searchType, Model model) {
		int pageSize = 10;

		Page<DayOffEntity> list = null;

		Pageable page = PageRequest.of(pageNum - 1, pageSize, Direction.DESC, "approval");

		if (search == null) {
			list = dayOffRepo.findAll(page);
		} else {
			if (searchType.equals("name")) {
				list = dayOffRepo.findByEmployeeNoNameContaining(search, page);
			} else if (searchType.equals("approval")) {
				list = dayOffRepo.findByApprovalContaining(search, page);
			}
		}
		// false : 조회한 데이터가 있음
		// true : 조회한 데이터가 없음
		boolean nullcheck = false; // 조회한 데이터의 유무를 확인하는 변수

		if (list.isEmpty()) {
			nullcheck = true;
		}
		model.addAttribute("nullcheck", nullcheck);

		long dno = departmentNo.getDepartmentNo();
		Page<DayOffEntity> appList = dayOffRepo.findAllByEmployeeNoDepartmentNoDepartmentNo(dno, page);
		model.addAttribute("appList", appList);
	}

	/*
	 * //결재 반려(삭제)
	 * 
	 * @Override public void delete(long dayOffNo) { DayOffEntity dayoff =
	 * dayOffRepo.findById(dayOffNo).orElseThrow(); dayOffRepo.deleteById(dayOffNo);
	 * }
	 */

	// 대표 결재리스트
	@Override
	public void approvalList2(Model model) {
		List<DayOffEntity> appList = dayOffRepo.findAllByApproval(AuthorizeStatus.FirstApproval);
		List<DayOffEntity> appList2 = dayOffRepo.findAllByApproval(AuthorizeStatus.Approval);
		List<DayOffEntity> appList3 = dayOffRepo.findAllByApproval(AuthorizeStatus.Return);
		appList.addAll(appList2);
		appList.addAll(appList3);
		model.addAttribute("appCEOList", appList);
	}

	@Override
	public void approvalList2(int pageNum, String search, String searchType, Model model) {
		int pageSize = 10;

		Page<DayOffEntity> list = null;

		Pageable page = PageRequest.of(pageNum - 1, pageSize, Direction.DESC, "approval");

		if (search == null) {
			list = dayOffRepo.findAll(page);
		} else {
			if (searchType.equals("name")) {
				list = dayOffRepo.findByEmployeeNoNameContaining(search, page);
			} else if (searchType.equals("approval")) {
				list = dayOffRepo.findByApprovalContaining(search, page);
			}
		}
		// false : 조회한 데이터가 있음
		// true : 조회한 데이터가 없음
		boolean nullcheck = false; // 조회한 데이터의 유무를 확인하는 변수

		if (list.isEmpty()) {
			nullcheck = true;
		}
		model.addAttribute("nullcheck", nullcheck);

		Page<DayOffEntity> appList = dayOffRepo.findAllByApprovalNot(AuthorizeStatus.UnderApproval, page);

		model.addAttribute("appCEOList", appList);
	}

	// 대표 결재 디테일
	@Override
	public void detail2(long dayOffNo, Model model) {
		DayOffEntity ent = dayOffRepo.findById(dayOffNo).orElseThrow();
		model.addAttribute("dayOffDetail", ent);
		model.addAttribute("detailEmp", ent.getEmployeeNo());
	}

	// 상태별 대표 결재리스트
	@Override
	public void findAllByAuthorizeStatus(Model model, String status) {
		System.out.println("status : " + status);

	}

	@Override
	public void findbyApproval(MyUserDetails myUserDetails, Model model) {

		EmployeesEntity emp = employeesRepo.findById(myUserDetails.getNo()).orElseThrow();

		List<DayOffEntity> approvalList = new ArrayList<>();

		if (myUserDetails.getPosition() == DepartmentRank.CEO) {
			System.err.println("CEO입니다");
			approvalList = dayOffRepo.findByEmployeeNoOrApproval(emp, AuthorizeStatus.FirstApproval);
		} else if (myUserDetails.getPosition() == DepartmentRank.DepartmentManager) {
			System.err.println("부장입니다");
			approvalList = dayOffRepo.findByEmployeeNoOrEmployeeNo_DepartmentNoAndApproval(emp, emp.getDepartmentNo(),
					AuthorizeStatus.UnderApproval);
		} else {
			System.err.println("사원입니다");
			approvalList = dayOffRepo.findByEmployeeNo(emp);
		}

		if (approvalList.size() > 10) {
			model.addAttribute("approvalList", approvalList.subList(0, 10));
		} else {
			model.addAttribute("approvalList", approvalList.subList(0, approvalList.size()));
		}

	}

	@Override
	public List<DayOffEntity> findbyApproval(AuthorizeStatus approval) {

		return dayOffRepo.findByApproval(approval);
	}

}
