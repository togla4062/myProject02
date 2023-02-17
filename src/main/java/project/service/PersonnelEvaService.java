package project.service;

import org.springframework.ui.Model;

import project.domain.DTO.EmployeesUpdateDTO;
import project.domain.DTO.PersonnelEvaDTO;

public interface PersonnelEvaService {
	
	int save(PersonnelEvaDTO dto,  EmployeesUpdateDTO empDto);

	void findByEmpNo(long no, Model model);
	
	void getNo(long no, Model model);

	void findById(Long no, Model model);
	
	void findAllByOrderByNoDesc(Model model);
	
	void findByEmpGrade(String empGrade);

	void findAllByAwardEMPForIndex(Model model);
  
}