package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import project.domain.DTO.BoardNoticeDTO;
import project.service.NoticeBoardService;


@Controller
public class NoticeBoardController {
	
	@Autowired
	private NoticeBoardService noticeservice;
	
	
	//공지사항 게시판 리스트 이동
	@GetMapping("/Board/noticeList")
    public String noticeList(@RequestParam(value="pageNum", required = false, defaultValue="1")int pageNum, 
			@RequestParam(value="search", required = false, defaultValue = "전체") String search,
			@RequestParam(value="searchType", required = false) String searchType,Model model) {
		noticeservice.findAll(pageNum,search,searchType,model);
        return "Board/noticeList";
    }
	
	//공지사항 글쓰기 페이지 이동
	@GetMapping("/Board/noticeWrite")
    public String noticeWrite() {
        return "Board/noticeWrite";
        
    }
	
	 //공지사항 디테일 페이지로 이동
	 @GetMapping("/noticeDetail")
	 public String boardDetail(@RequestParam long noticeNo, Model model) {
		 
		 //게시글 정보를 가져오는 기능
		 noticeservice.detail(noticeNo, model);
		 return "Board/noticeDetail";
	 }
	
	//공지사항 글쓰기 기능
	 @PostMapping("/Board/noticeWrite")   
	    public String noticeWriting(@ModelAttribute("dto") BoardNoticeDTO dto, BindingResult result) {
		 
		 if(result.hasErrors()) {
			 return "Board/noticeWrite";
		 }else {
			 noticeservice.save(dto);
			 return "redirect:/Board/noticeList";
		 }
    }
	 
	//공지사항 게시글 삭제
	//spring.mvc.hiddenmethod.filter.enabled=true
	//POST-> DELETE
	@DeleteMapping("/Board/noticeList/{noticeNo}")
	public String delete(@PathVariable long noticeNo) {
		noticeservice.delete(noticeNo);
		return "redirect:/Board/noticeList";
	}
	
	//공지사항 게시글 수정
	@PostMapping("/noticeEdit")
	public String noticeEdit(BoardNoticeDTO dto, long noticeNo,RedirectAttributes redirectAttributes) {
		noticeservice.update(dto, noticeNo);
		redirectAttributes.addAttribute("noticeNo", noticeNo);
		return "redirect:/noticeDetail";
	}
 
}
