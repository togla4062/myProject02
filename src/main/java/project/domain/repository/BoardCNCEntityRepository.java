package project.domain.repository;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.BoardCNCEntity;

@Repository
public interface BoardCNCEntityRepository extends JpaRepository<BoardCNCEntity, Long>{


	/* Page<BoardCNCEntity> findAllByOrderByEventDateAsc(); */

	Page<BoardCNCEntity> findByTitleContaining(String search, Pageable page);

	Page<BoardCNCEntity> findByContentContaining(String search, Pageable page);

	Page<BoardCNCEntity> findByRegistNo_nameContaining(String search, Pageable page);

	List<BoardCNCEntity> findByEventDateAfter(LocalDate minusDays);
	
	
}
