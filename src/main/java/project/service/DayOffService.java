package project.service;


import org.springframework.data.domain.Pageable;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

import org.springframework.ui.Model;

import project.domain.DTO.DayOffAppDTO;
import project.domain.DTO.DayOffInsertDTO;
import project.domain.entity.DayOffEntity;
import project.domain.entity.DepartmentsEntity;
import project.enums.AuthorizeStatus;

import project.security.MyUserDetails;

public interface DayOffService {

	void save(DayOffInsertDTO dto, HttpServletResponse response);

	void update(DayOffInsertDTO dto);

	void personalDayOff(long no, Model model);

	void mydayoff(long no, Model model);

	void detail(long dayOffNo, Model model);

	void appList(DepartmentsEntity departmentNo, int pageNum, String search, String searchType, Model model);

	//void delete(long dayOffNo);

	void approvalList2(int pageNum, String search, String searchType, Model model);

	void detail2(long dayOffNo, Model model);

	void findAllByAuthorizeStatus(Model model, String status);

	//void delete(long dayOffNo);

	void approvalList2(Model model);

	void findbyApproval(MyUserDetails myUserDetails, Model model);

	List<DayOffEntity> findbyApproval(AuthorizeStatus approval);

	//void approval(DayOffAppDTO dto, long dayOffNo);

}