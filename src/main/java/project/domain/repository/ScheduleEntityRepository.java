package project.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.EmployeesEntity;
import project.domain.entity.ScheduleEntity;

@Repository
public interface ScheduleEntityRepository extends JpaRepository<ScheduleEntity, Long>{

	List<ScheduleEntity> findByemployeeNo(EmployeesEntity emp);

}
