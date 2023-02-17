package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.fes.FestivalBaseInfo;

public interface FesService {

	void getFirstResult(String pageNo, ModelAndView mv);

	void getSearchResult(String pageNo, String name, ModelAndView mv);

}
