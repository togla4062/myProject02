package project.service.proc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.BoardSuggestionsDTO;
import project.domain.entity.BoardSuggestionsEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ReplySuggestionsEntity;
import project.domain.repository.BoardSuggestionsEntityRepository;
import project.domain.repository.EmployeesEntityRepository;
import project.service.ReplySuggestionService;
import project.service.SuggestionBoardService;

@Service
public class SuggestionBoardServiceProc implements SuggestionBoardService {

	/* 20230110 문대현 생성 */

	@Autowired
	BoardSuggestionsEntityRepository suggestionsRepository;

	@Autowired
	EmployeesEntityRepository employeesRepository;

	@Autowired
	ReplySuggestionService replySuggestionService;

	/**건의사항 게시글 리스트페이지에 출력할 모든 데이터 조회 서비스
	 * 
	 * @param pageNum		페이지번호
	 * @param search		검색할 단어
	 * @param searchType	검색할 종류(제목,내용,작성자)
	 * @param model			페이지에 데이터 연결용
	 * @return				페이지 주소
	 */
	@Override
	public void findAllList(int pageNum, String search, String searchType, Model model) {

		//한 페이지에 표현해줄 리스트 갯수
		int pageSize = 10;
		
		// 리스트 페이지에 출력해줄 데이터리스트
		Page<BoardSuggestionsEntity> list = null;

		// 페이징기능(페이지인덱스번호,페이지 사이즈,정렬방식,정렬할 컬럼이름)
		Pageable page = PageRequest.of(pageNum - 1, pageSize, Direction.DESC, "createDate");
		
		if (search.equals("전체")){
			// 만약 검색한 내용이 없다면 전체 리스트 정보 가져오기
			list = suggestionsRepository.findAll(page);
		} else {

			if (searchType.equals("title")) {
				// 만약 검색한 내용이 제목을 검색한 것이라면 해당 리스트를 가져오기
				list = suggestionsRepository.findByTitleContaining(search, page);
			}else if(searchType.equals("content")) {
				// 만약 검색한 내용이 내용을 검색한 것이라면 해당 리스트를 가져오기
				list = suggestionsRepository.findByContentContaining(search, page);
			}else if(searchType.equals("name")) {
				// 만약 검색한 내용이 작성자를 검색한 것이라면 해당 리스트를 가져오기
				list = suggestionsRepository.findByRegistNo_nameContaining(search, page);
			}
		}
		
		model.addAttribute("searchType", searchType);
		model.addAttribute("search", search);
		model.addAttribute("suggestionList", list);
	}

	// 건의사항 DB에 데이터를 저장하는 서비스
	@Override
	public void save(BoardSuggestionsDTO dto) {

		// 사번으로 사원정보 조회
		EmployeesEntity emp = employeesRepository.findById(dto.getNo()).orElseThrow();

		// 건의사항 테이블에 저장
		suggestionsRepository.save(dto.toEntityForSave(emp));
	}

	// 건의사항 게시글 내용 업데이트 기능
	@Override
	public void update(BoardSuggestionsDTO dto, long suggestNo) {

		// 사번으로 사원정보 조회
		EmployeesEntity emp = employeesRepository.findById(dto.getNo()).orElseThrow();

		// 업데이트 기능
		suggestionsRepository.save(dto.toEntityForUpdate(suggestNo, emp));
	}

	// 건의사항 디테일 페이지 데이터 조회하는 서비스
	@Override
	public void detail(long suggestNo, Model model) {

		// 게시글번호로 해당 게시글 정보 조회
		BoardSuggestionsEntity entityData = suggestionsRepository.findById(suggestNo).orElseThrow();

		model.addAttribute("suggestionDetail", entityData);
	}

	// 건의사항 게시글 삭제 기능
	@Override
	public void delete(long suggestNo) {

		// 게시글 번호로 게시글 정보 조회
		BoardSuggestionsEntity suggestions = suggestionsRepository.findById(suggestNo).orElseThrow();

		// 조회된 게시글 번호로 해당 게시글을 참조하고 있는 모든 댓글리스트를 조회
		List<ReplySuggestionsEntity> reply = replySuggestionService.findBySuggestNo(suggestions);

		// 해당 게시글의 댓글리스트를 모두 삭제해 주는 기능
		for (ReplySuggestionsEntity list : reply) {
			replySuggestionService.deleteById(list.getReplySuggestNo());
		}

		// 해당 게시글을 삭제
		suggestionsRepository.deleteById(suggestNo);
	}
	// 인덱스에 띄워줄 건의사항 게시글 내용
	@Override
	public void findListForIndex(ModelAndView modelAndView) {
		
		Pageable page = PageRequest.of(0, 5, Direction.DESC, "createDate");

		modelAndView.addObject("boardPage", suggestionsRepository.findAll(page));
		modelAndView.addObject("url", "/suggestDetail" );
		modelAndView.addObject("boardNo", "suggestNo" );
		
	}
	 static boolean isStringEmpty(String str) {
	        return str == null || str.isEmpty();
	    }
}
