package project.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.domain.DTO.PersonnelEvaDTO;
import project.domain.entity.PersonnelEvaEntity;

@Repository
public interface PersonnelEvaRepository extends JpaRepository<PersonnelEvaEntity, Long>{
	
	Optional<PersonnelEvaEntity> findByEmpNo(long no);
	
	PersonnelEvaEntity deleteByEmpNo(long no);
	
	
}