package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.domain.DTO.BoardSuggestionsDTO;
import project.domain.DTO.ReplySuggestionsDTO;
import project.service.ReplySuggestionService;
import project.service.SuggestionBoardService;

@Controller
public class SuggestionBoardController {

	@Autowired
	SuggestionBoardService suggestionservice;

	@Autowired
	ReplySuggestionService replySuggestionService;

	
	/**건의 게시판 리스트 페이지 이동
	 * 
	 * @param pageNum		페이지번호
	 * @param search		검색할 단어
	 * @param searchType	검색할 종류(제목,내용,작성자)
	 * @param model			페이지에 데이터 연결용
	 * @return				페이지 주소
	 */
	@GetMapping("/Board/suggestionList")
	public String suggestionList(@RequestParam(value="pageNum", required = false, defaultValue="1")int pageNum, 
			@RequestParam(value="search", required = false, defaultValue = "전체") String search,
			@RequestParam(value="searchType", required = false) String searchType,Model model) {
		suggestionservice.findAllList(pageNum,search,searchType,model);
		return "Board/suggestionList";
	}
	
	// 건의사항 글쓰기 페이지 이동
	@GetMapping("/Board/suggestionWrite")
	public String suggestionWrite() {
		return "Board/suggestionWrite";
	}

	// 건의사항 글쓰기 기능
	@PostMapping("/Board/suggestionWrite")
	public String suggestionWriting(BoardSuggestionsDTO dto) {
		suggestionservice.save(dto);
		return "redirect:/Board/suggestionList";
	}
	
	// 건의사항 내용 수정 기능
	@PostMapping("/suggestionEdit")
	public String suggestionEdit(BoardSuggestionsDTO dto, long suggestNo, RedirectAttributes redirectAttributes) {
		suggestionservice.update(dto,suggestNo);
		redirectAttributes.addAttribute("suggestNo", suggestNo);
		return "redirect:/suggestDetail";
	}
	
	// 건의사항 게시글에서 제목이 눌렀을 경우 해당 게시글 상세 페이지로 이동
	@GetMapping("/suggestDetail")
	public String suggestDetail(@RequestParam long suggestNo, @RequestParam(value="pageNum", required = false, defaultValue="1")int pageNum, Model model) {

		// 게시글 정보를 가져오는 기능
		suggestionservice.detail(suggestNo, model);

		// 해당 게시글의 댓글정보를 가져오는 기능
		replySuggestionService.findAllList(suggestNo,pageNum, model);

		return "Board/suggestionDetail";
	}
	
	// 건의사항 게시글 삭제 기능
	@PostMapping("/boardDelete")
	public String boardDelete(long suggestNo) {
		suggestionservice.delete(suggestNo);
		return "redirect:/Board/suggestionList";
	}

	///// 댓글관련 //////

	// 댓글 작성시 저장 후 해당 게시글의 디테일 페이지로 리다이렉트
	@PostMapping("/ReplySuggestionWrite")
	public String ReplySuggestionWrite(ReplySuggestionsDTO dto, RedirectAttributes redirect) {
		
		replySuggestionService.save(dto); // 댓글 저장 기능
		redirect.addAttribute("suggestNo", dto.getSuggestNo()); // 리다이렉트 시에 데이터를 넘겨주는 기능
		
		return "redirect:/suggestDetail";
	}
	
	// 댓글 내용 업데이트 완료 후 해당 게시글의 디테일 페이지로 리다이렉트
	@PostMapping("/suggestionReplyEdit")
	public String suggestionReplyEdit(ReplySuggestionsDTO dto, long replySuggestNo, RedirectAttributes redirectAttributes) {
		
		replySuggestionService.update(dto,replySuggestNo); // 댓글 업데이트 기능
		redirectAttributes.addAttribute("suggestNo", dto.getSuggestNo()); // 리다이렉트 시에 데이터를 넘겨주는 기능
		
		return "redirect:/suggestDetail";
	}
	
	//댓글 삭제 후 해당 게시글의 디테일 페이지로 리다이렉트
	@PostMapping("/suggestionReplyDelete")
	public String suggestionReplyDelete(long replySuggestNo, long suggestNo, RedirectAttributes redirectAttributes) {
		
		replySuggestionService.deleteById(replySuggestNo); // 댓글 삭제 기능
		redirectAttributes.addAttribute("suggestNo", suggestNo); // 리다이렉트 시에 데이터를 넘겨주는 기능
		
		return "redirect:/suggestDetail";
	}
}
