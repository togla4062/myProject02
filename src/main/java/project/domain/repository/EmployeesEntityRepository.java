package project.domain.repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import project.domain.entity.EmployeesEntity;

@Repository
public interface EmployeesEntityRepository extends JpaRepository<EmployeesEntity, Long>{


	EmployeesEntity findByEmail(String username);

	EmployeesEntity findAllByNo(long employeeNo);

	List<EmployeesEntity> findAllByDepartmentNoDepartmentNoAndDeleteStatusOrderByPositionRank(Long department, boolean b); //한아 작성

	List<EmployeesEntity> findAllByDeleteStatusOrderByPositionRank(boolean b); //한아 작성
	Page<EmployeesEntity> findAllByDeleteStatusOrderByPositionRank(boolean b, Pageable pa); //한아 전체 리스트 페이징

	List<EmployeesEntity> findAllByDepartmentNoDepartmentNo(Long department);

	EmployeesEntity findByPositionRank(long i);

	/* List<EmployeesEntity> findAllByOrderByEmpGradeDesc(); //수민 잠깐 생성! 1/17*/
	
 	List<EmployeesEntity> findAllByOrderByNoDesc();
 	
 	List<EmployeesEntity> findAllByEmpGrade(String empGrade);

	List<EmployeesEntity> findAllByDeleteStatusOrderByDepartmentNoDesc(boolean b);

	Page<EmployeesEntity> findAllByDepartmentNoDepartmentNoAndDeleteStatus(Long department, boolean b,
			Pageable pageable);

	Page<EmployeesEntity> findAllByDeleteStatusAndNameContaining(boolean b, String keyword, Pageable pageable);

	Page<EmployeesEntity> findAllByDeleteStatus(boolean b, Pageable pageable);

	int countAllByDeleteStatus(boolean b); //한아 전체 리스트 페이징

	Optional<EmployeesEntity> findByName(String headname); //한아 부서장 변경시 직급 부장으로 변경

	Page<EmployeesEntity> findByNameContaining(String search, Pageable page); //이름으로 검색
	Page<EmployeesEntity> findByEmailContaining(String search, Pageable page); //이메일로 검색

	List<EmployeesEntity> findAllByDeleteStatus(boolean b);

	Optional<EmployeesEntity> findByNo(long dayOffNo);

	Page<EmployeesEntity> findAllByDepartmentNoDepartmentNoAndDeleteStatusOrderByPositionRank(Long department,
			boolean b, Pageable pageable);


	Page<EmployeesEntity> findAllByJoinDateBetween(LocalDate first, LocalDate last, Pageable page);


}