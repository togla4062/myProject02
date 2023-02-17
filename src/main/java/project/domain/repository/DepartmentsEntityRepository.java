package project.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.DepartmentsEntity;

@Repository
public interface DepartmentsEntityRepository extends JpaRepository<DepartmentsEntity, Long>{

	List<DepartmentsEntity> findAllByDepartmentNo(long departmentNo);

	/* 230116 한아 작성 */

}
