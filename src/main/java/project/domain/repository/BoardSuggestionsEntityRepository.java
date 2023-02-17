package project.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.BoardSuggestionsEntity;

@Repository
public interface BoardSuggestionsEntityRepository extends JpaRepository<BoardSuggestionsEntity, Long>{

	Page<BoardSuggestionsEntity> findByTitleContaining(String search, Pageable page);

	Page<BoardSuggestionsEntity> findByContentContaining(String search, Pageable page);

	Page<BoardSuggestionsEntity> findByRegistNo_nameContaining(String search, Pageable page);

}
