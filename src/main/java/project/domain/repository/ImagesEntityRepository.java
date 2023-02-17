package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.ImagesEntity;

@Repository
public interface ImagesEntityRepository extends JpaRepository<ImagesEntity, Long>{
	/* 230109 한아 작성 */

}
