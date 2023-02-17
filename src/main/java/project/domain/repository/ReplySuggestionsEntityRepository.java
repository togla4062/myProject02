package project.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.BoardSuggestionsEntity;
import project.domain.entity.ReplySuggestionsEntity;

@Repository
public interface ReplySuggestionsEntityRepository extends JpaRepository<ReplySuggestionsEntity,Long>{

	Page<ReplySuggestionsEntity> findBySuggestNo(BoardSuggestionsEntity board, Pageable page);

	List<ReplySuggestionsEntity> findBySuggestNo(BoardSuggestionsEntity suggestions);

}
