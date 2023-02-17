package project.controller.hr;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.domain.DTO.EmployeesUpdateDTO;
import project.domain.repository.EmployeesEntityRepository;
import project.enums.MyRole;

@Controller
public class MyRoleController {
	/* 230112 한아 작성 */
	
	@Autowired
	EmployeesEntityRepository employeesRepo;
	
	//수정권한부여
	@Transactional
	@GetMapping("/role/manager/{no}")
	public String roleManager(@PathVariable long no, EmployeesUpdateDTO dto) {
		employeesRepo.findById(no).get().addRole(MyRole.PERSONALMANAGER);
		employeesRepo.findById(no).map(entity->entity.updateManager(dto));
		return "redirect:/ozc/groupDetail/{no}";
	}
	
	//수정권한제거
	@Transactional
	@GetMapping("/role/employee/{no}")
	public String roleEmployee(@PathVariable long no, EmployeesUpdateDTO dto) {
		employeesRepo.findById(no).get().addRole(MyRole.EMPLOYEE);
		employeesRepo.findById(no).map(entity->entity.updateEmployee(dto));
		return "redirect:/ozc/groupDetail/{no}";
	}

}
