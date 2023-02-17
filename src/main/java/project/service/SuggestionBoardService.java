package project.service;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.BoardSuggestionsDTO;

public interface SuggestionBoardService {

	void save(BoardSuggestionsDTO dto);

	void findAllList(int pageNum, String search, String searchType, Model model);

	void detail(long suggestNo, Model model);

	void update(BoardSuggestionsDTO dto, long suggestNo);

	void delete(long suggestNo);

	void findListForIndex(ModelAndView modelAndView);

}
