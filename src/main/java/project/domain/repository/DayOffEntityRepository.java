package project.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import project.domain.entity.DayOffEntity;
import project.domain.entity.DepartmentsEntity;
import project.domain.entity.EmployeesEntity;
import project.enums.AuthorizeStatus;

public interface DayOffEntityRepository extends JpaRepository<DayOffEntity, Long>{

	List<DayOffEntity> findByEmployeeNo(EmployeesEntity emp);

	DayOffEntity findAllByDayOffNo(long no);

	List<DayOffEntity> findAllByDayOffNo(EmployeesEntity emp);

	List<DayOffEntity> findAllByEmployeeNo(EmployeesEntity findByDepartmentNo);

	DayOffEntity findByDayOffNo(long dayOffNo);

	List<DayOffEntity> findAllByApproval(AuthorizeStatus firstapproval);

	List<DayOffEntity> findByEmployeeNoOrEmployeeNo_DepartmentNoAndApproval(EmployeesEntity emp, DepartmentsEntity departmentNo,
			AuthorizeStatus approval);

	List<DayOffEntity> findByEmployeeNoOrApproval(EmployeesEntity emp, AuthorizeStatus firstapproval);

	List<DayOffEntity> findByApproval(AuthorizeStatus approval);

	Page<DayOffEntity> findByEmployeeNoNameContaining(String search, Pageable page);

	Page<DayOffEntity> findByApprovalContaining(String search, Pageable page);

	Page<DayOffEntity> findAllByEmployeeNoDepartmentNoDepartmentNo(long dno, Pageable page);

	Page<DayOffEntity> findAllByApprovalNot(AuthorizeStatus underapproval, Pageable page);

	List<DayOffEntity> findByEmployeeNoOrderByStartDate(EmployeesEntity orElseThrow);

}
