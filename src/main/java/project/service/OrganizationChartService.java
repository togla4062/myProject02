package project.service;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.EmployeesUpdateDTO;

public interface OrganizationChartService {

	void findAllByDepartmentNo(Model model, Long department);

	void findById(Model model, Long no);

	void findByIdEditMode(Model model, Long no);
	
	void findAllByDeleteStatusFalse(Model model, Pageable pageable); //수민(PersonnelEvaController)
	
	void findAllByDeleteStatusTrue(Model model);

	void editmode(Long no, EmployeesUpdateDTO dto);
	
	void findAllList(Model model); //eva

	void findCEO(Model model); //familyTree

	void findDepartmentHead(Model model); //familyTree

	void treelist(Model model, Long no);

	void listForAjax(ModelAndView mv, int page); //한아 전체리스트 페이징

	void findAllList(int pageNum, String search, String searchType, Model model); //검색하기





}
