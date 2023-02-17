package project.controller.hr;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import project.domain.DTO.DepartmentsUpdateDTO;
import project.domain.DTO.EmployeesDeleteDTO;
import project.domain.DTO.EmployeesUpdateDTO;
import project.domain.repository.DepartmentsEntityRepository;
import project.domain.repository.EmployeesEntityRepository;
import project.enums.DepartmentRank;
import project.enums.MyRole;

@Controller
public class RetirementController {
	/* 230112 한아 작성 */
	
	@Autowired
	EmployeesEntityRepository employeesRepo;
	
	@Autowired
	DepartmentsEntityRepository departmentsRepo;
	
	//퇴직처리
	@Transactional
	@GetMapping("/retirement/delete/{no}")
	public String roleManager(@PathVariable long no, EmployeesDeleteDTO dto, DepartmentsUpdateDTO udto) {
		employeesRepo.findById(no).get().addRole(MyRole.NONE);
		employeesRepo.findById(no).map(entity->entity.updateDeleteStatus(dto));
		long departmentNo = employeesRepo.findById(no).get().getDepartmentNo().getDepartmentNo();
		departmentsRepo.findById(departmentNo).map(entity->entity.updateDepartmentHead(udto)); //부서장 퇴직처리 되면 해당부서 부서장 '미정'
		System.out.println("퇴직처리완료");
		return "redirect:/ozc/groupDetail/{no}";
	}
	
	//퇴직처리취소
	@Transactional
	@GetMapping("/retirement/rollback/{no}")
	public String roleEmployee(@PathVariable long no, EmployeesUpdateDTO dto) {
		MyRole role = null;
		if(employeesRepo.findById(no).get().getPosition() == DepartmentRank.DepartmentManager) role = MyRole.PERSONALMANAGER;
		else if(employeesRepo.findById(no).get().getPosition() == DepartmentRank.CEO) role = MyRole.CEO;
		else role = MyRole.EMPLOYEE;
		employeesRepo.findById(no).get().addRole(role);
		employeesRepo.findById(no).map(entity->entity.updateRollbackStatus(dto));
		System.out.println("퇴직처리취소");
		return "redirect:/ozc/groupDetail/{no}";
	}


}
