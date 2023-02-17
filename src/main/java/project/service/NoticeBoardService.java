package project.service;


import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.BoardNoticeDTO;


public interface NoticeBoardService {

	void save(BoardNoticeDTO dto);
	
	void detail(long noticeNo, Model model);

	void findAll(int pageNum, String search, String searchType, Model model);

	void delete(long noticeNo);

	void update(BoardNoticeDTO dto, long suggestNo);

	void findListForIndex(ModelAndView modelAndView);
}
