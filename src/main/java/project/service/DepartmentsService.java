package project.service;

import org.springframework.ui.Model;

import project.domain.DTO.DepartmentsUpdateDTO;

public interface DepartmentsService {
	/* 230116 한아 작성 */
	
	void departmentEditMode(Long departmentNo, Model model);

	void editDepartment(long departmentNo, DepartmentsUpdateDTO dto);

}
