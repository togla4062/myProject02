package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import project.domain.DTO.EmployeesDetailDTO;
import project.domain.DTO.EmployeesUpdateDTO;
import project.domain.DTO.PersonnelEvaDTO;
import project.domain.repository.DepartmentsEntityRepository;
import project.service.EmployeesService;
import project.service.OrganizationChartService;
import project.service.PersonnelEvaService;

@Controller
public class PersonnelEvaController {
	
	@Autowired
	OrganizationChartService organizationChartService; 
	
	@Autowired
	private DepartmentsEntityRepository departmentRepo;
	
	@Autowired
	private PersonnelEvaService personnelEvaService;
	
	@Autowired
	private EmployeesService employeesService;
	
	// 인사관리 평가 메인리스트페이지  //변경햇슴  1/17 수민
	@GetMapping("/personnelEvaList")
	public String personnelEvaMain(Model model, @PageableDefault(size = 10)Pageable pageable) {
		model.addAttribute("department", departmentRepo.findAll());
		organizationChartService.findAllByDeleteStatusFalse(model,pageable);	
		employeesService.findByEmpGrade("A", model);
		return "personnel/persommelEvaList";
	}
	
	
	@GetMapping("/personnelEvaList/{department}")
	public String groupListByDepartmentNo(Model model, @PathVariable Long department) {
		System.out.println(" >>>>> here?");
		organizationChartService.findAllByDepartmentNo(model, department);
		model.addAttribute("department", departmentRepo.findAll());
		model.addAttribute("departmentNo", department);
		return "personnel/personnerlByDepartment";
	}
	
	// 인사관리 평가 페이지
	@GetMapping("/personnelEva/{no}")
	public String personnelEva(Model model, @PathVariable Long no) {
		personnelEvaService.findById(no, model); //수정함 1/17 수민
		
		return "personnel/personnelEva";
	}
	
	@ResponseBody
	@PostMapping("/personnelEva/save")
	public String personnelEvaSave(@RequestBody PersonnelEvaDTO dto, EmployeesUpdateDTO empDto ) {
		
		int num = personnelEvaService.save(dto , empDto);
		Gson gson = new Gson();
		
		JsonObject jsonObject =new JsonObject();
		
		
		if(num==1) {
			jsonObject.addProperty("msg", "SUCCESS");
		}
		else {
			jsonObject.addProperty("msg", "FAIL");
		}
		String result = gson.toJson(jsonObject);
		
		return result;
	}
	
	/*
	  @PostMapping("/personnelEva/bestEmp")
	  
	  @ResponseBody public String findEmpGet(@RequestBody PersonnelEvaDTO dto) {
	  
	  List<EmployeesDetailDTO> bestList = personnelEvaService.findByEmpGrade("A");
	  
	  if(bestList.size() == 0 ) return new String();
	  
	  Gson gson = new Gson(); String result = gson.toJson(bestList);
	  
	 * return result;
	 * 
	 * }
	 */
	
	@GetMapping("/personnelEva/modal")
	public String bestListModal() {
		return"/personnel/bestEmpModal";
	}
	

}
