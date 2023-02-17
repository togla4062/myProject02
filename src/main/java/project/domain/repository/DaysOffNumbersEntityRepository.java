package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import project.domain.entity.DaysOffNumbersEntity;
import project.domain.entity.EmployeesEntity;

public interface DaysOffNumbersEntityRepository extends JpaRepository<DaysOffNumbersEntity, Long>{

	DaysOffNumbersEntity findByNo(EmployeesEntity emp);
	

}
