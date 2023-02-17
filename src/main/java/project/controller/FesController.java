package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import project.service.FesService;

@RestController
public class FesController {

	@Autowired
	private FesService fesService;

	// 저장소
	private String name;

	// 광주광역시 축제 검색 페이지로 이동합니다.
	@GetMapping("/fes")
	public ModelAndView fesSearchPage(ModelAndView mv) {
		mv.setViewName("fes/main");
		return mv;
	}

	// 첫 페이지 데이터 ajax요청 / 페이징 처리입니다.
	@GetMapping("/fes/{pageNo}")
	public ModelAndView getFes(@PathVariable() String pageNo, ModelAndView mv) {
		fesService.getFirstResult(pageNo, mv);
		mv.setViewName("fes/firstresult");
		return mv;
	}

	// 검색 결과 데이터 ajax요청 / 페이징 처리입니다.
	@GetMapping("/search/{pageNo}")
	public ModelAndView fessearch(@PathVariable() String pageNo, String festivalname, ModelAndView mv) {
		// null인 경우 - 검색결과에서 페이지만 이동할 때(festivalname은 이전의 검색결과값을 유지해야함 따라서 name에서 불러옴)
		if (festivalname == null) {festivalname = name;} 
		
		// null아닌 경우 - 검색내용을 입력했을 때 name에 저장해서 페이징 처리시 불러온다.(처음 검색어입력시 검색어가 저장된다.)
		name = festivalname;

		System.out.println("!!!!!" + pageNo + name);
		
		fesService.getSearchResult(pageNo, name, mv);
		mv.setViewName("fes/searchresult");
		return mv;
	}
}
