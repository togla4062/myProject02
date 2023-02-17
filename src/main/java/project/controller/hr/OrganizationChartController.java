package project.controller.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.EmployeesUpdateDTO;
import project.domain.repository.DepartmentsEntityRepository;
import project.service.OrganizationChartService;

@Controller
public class OrganizationChartController {
	
	/* 230106 한아 작성 */

	@Autowired
	OrganizationChartService organizationChartService; 
	
	@Autowired
	private DepartmentsEntityRepository departmentRepo;
	
	//조직도 리스트(페이징)
	@ResponseBody //ModelAndView의 ViewName에 설정된 HTML 응답객체로 사용
	@GetMapping("/ozc/groupList/all/{page}")
	public ModelAndView groupList(@PathVariable int page, ModelAndView mv) {
		organizationChartService.listForAjax(mv,page);
		mv.setViewName("organizationChart/groupList");
		return mv;
	}
	/** 검색하기
	 * 
	 * @param pageNum		페이지번호
	 * @param search		검색할 단어
	 * @param searchType	검색할 종류(사원명,부서,직급,내선번호,주업무)
	 * @param model			페이지에 데이터 연결용
	 * @return				페이지 주소
	 */
	@GetMapping("/ozc/groupList/search")
	public String suggestionList(@RequestParam(value="pageNum", required = false, defaultValue="1")int pageNum, 
			@RequestParam(value="search", required = false, defaultValue = "전체") String search,
			@RequestParam(value="searchType", required = false) String searchType,Model model) {
		organizationChartService.findAllList(pageNum,search,searchType,model);
		return "organizationChart/groupListSearch";
	}
	//퇴직사원 리스트
	@GetMapping("/ozc/groupList/retirement")
	public String groupListDeleted(Model model) {
		organizationChartService.findAllByDeleteStatusTrue(model);
		return "organizationChart/groupListRetirement";
	}
	//부서별 조직도 리스트
	//@ResponseBody //페이지 이동(.html)하기 위해서는 이거 쓰면 안됨
	@GetMapping("/ozc/groupList/{department}")
	public String groupListByDepartmentNo(Model model, @PathVariable Long department) {
		System.out.println(" >>>>> here?");
		organizationChartService.findAllByDepartmentNo(model, department);
		model.addAttribute("department", departmentRepo.findAll());
		model.addAttribute("departmentNo", department);
		return "organizationChart/groupListByDepartment";
	}
	//조직도 상세페이지
	@GetMapping("/ozc/groupDetail/{no}")
    public String groupDetail(Model model, @PathVariable Long no) {
		if(no==0) return "redirect:/departments/manage";
		organizationChartService.findById(model, no);
        return "organizationChart/groupDetail";
    }
	
	//사원 상세페이지 수정모드
	@GetMapping("/ozc/groupDetail/edit/{no}")
	public String groupDetailEdit(Model model, @PathVariable Long no) {
		organizationChartService.findByIdEditMode(model, no);
		model.addAttribute("department", departmentRepo.findAll());
		return "organizationChart/groupDetailEditMode";
	}
	//사원 상세페이지 수정완료
	@GetMapping("/ozc/groupDetail/edited/{no}")
	public String groupDetailEdited(@PathVariable Long no, EmployeesUpdateDTO dto) {
		organizationChartService.editmode(no, dto);
		return "redirect:/ozc/groupDetail/{no}";
	}
	//Family Tree
	@GetMapping("/ozc/familytree")
	public String familytree(Model model) {
		organizationChartService.findCEO(model); //대표이사정보
		model.addAttribute("department", departmentRepo.findAll()); //부서정보불러오기
		organizationChartService.findDepartmentHead(model);//부서장이미지가져오기
		return "organizationChart/familyTree";
	}
}

