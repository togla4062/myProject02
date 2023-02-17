package project.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import project.domain.DTO.EmployeesDeleteDTO;
import project.domain.DTO.EmployeesInsertDTO;
import project.domain.entity.EmployeesEntity;

public interface EmployeesService {
	/* 230106 한아 작성 */

	Map<String, String> fileTempUpload(MultipartFile gimg);

	void save(EmployeesInsertDTO dto);

	List<EmployeesEntity> findAll();

	void findemployee(long no, Model model);

	void findAllByNewEMPForIndex(Model model);

	void findByEmpGrade(String empGrade, Model model);
}

