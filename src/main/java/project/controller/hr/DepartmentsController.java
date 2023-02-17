package project.controller.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import project.domain.DTO.DepartmentsUpdateDTO;
import project.domain.entity.DepartmentsEntity;
import project.domain.repository.DepartmentsEntityRepository;
import project.service.DepartmentsService;

@Controller
public class DepartmentsController {
	
	@Autowired
	private DepartmentsService departmentsService;
	
	@Autowired
	private DepartmentsEntityRepository departmentRepo;
	
	//부서관리페이지
	@GetMapping("/departments/manage")
	public String manageDepartments(Model model) {
		model.addAttribute("departmentList", departmentRepo.findAll());
		return "organizationChart/departmentManagement";
	}
	//부서등록
	@PostMapping("/departments/reg")
	public String registDepartments(Model model, String departmentName, String departmentHead) {
		departmentRepo.save(DepartmentsEntity.builder()
				.departmentName(departmentName)
				.departmentHead("미정")
				.build());
		return "redirect:/departments/manage";
	}
	//부서수정삭제모드
	@GetMapping("/departments/editdelete/{departmentNo}")
	public String editDepartments(@PathVariable Long departmentNo, Model model) {
		departmentsService.departmentEditMode(departmentNo, model);
		return "organizationChart/departmentEditMode";
	}
	//부서수정완료
	@GetMapping("/departments/edit/{departmentNo}")
	public String editingDepartments(@PathVariable long departmentNo, DepartmentsUpdateDTO dto) {
		departmentsService.editDepartment(departmentNo, dto);
		return "redirect:/departments/manage";
	}
	//부서삭제
	@PostMapping("/departments/delete/{departmentNo}")
	public String deletingDepartments(@PathVariable long departmentNo) {
		departmentRepo.deleteById(departmentNo);
		return "redirect:/departments/manage";
	}
}
