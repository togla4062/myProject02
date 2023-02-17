package project.service;

import java.util.List;

import org.springframework.ui.Model;

import project.domain.DTO.ReplySuggestionsDTO;
import project.domain.entity.BoardSuggestionsEntity;
import project.domain.entity.ReplySuggestionsEntity;

public interface ReplySuggestionService {

	void save(ReplySuggestionsDTO dto);

	void findAllList(long suggestNo, int pageNum, Model model);

	void update(ReplySuggestionsDTO dto, long replySuggestNo);

	void deleteById(long replySuggestNo);

	List<ReplySuggestionsEntity> findBySuggestNo(BoardSuggestionsEntity suggestions);

}
