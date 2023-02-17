package project.service.proc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.BoardNoticeDTO;
import project.domain.entity.BoardNoticeEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.repository.BoardNoticeRepository;
import project.domain.repository.EmployeesEntityRepository;
import project.service.NoticeBoardService;

@Service
public class NoticBoardServiceProc implements NoticeBoardService {
	@Autowired
	BoardNoticeRepository noticeRepository;

	@Autowired
	EmployeesEntityRepository employeesRepository;

	// 공지사항 DB에 데이터를 저장하는 서비스
	@Override
	public void save(BoardNoticeDTO dto) {

		EmployeesEntity em = employeesRepository.findById(dto.getNo()).orElseThrow();

		noticeRepository.save(dto.toEntityForSave(em));

	}

	// 공지사항 디테일 페이지 조회하는 서비스
	@Override
	public void detail(long noticeNo, Model model) {

		BoardNoticeEntity entityData = noticeRepository.findById(noticeNo).orElseThrow();

		model.addAttribute("noticeDetail", entityData);

	}

	// 공지사항 게시글 리스트페이지에 출력할 모든 데이터 조회 서비스
	@Override
	public void findAll(int pageNum, String search, String searchType, Model model) {
		// 한 페이지에 표현해줄 리스트 갯수
		int pageSize = 10;

		// 리스트 페이지에 출력해줄 데이터리스트
		Page<BoardNoticeEntity> list = null;

		// 페이징기능(페이지인덱스번호,페이지 사이즈,정렬방식,정렬할 컬럼이름)
		Pageable page = PageRequest.of(pageNum - 1, pageSize, Direction.DESC, "createDate");

		if (search.equals("전체")) {
			// 만약 검색한 내용이 없다면 전체 리스트 정보 가져오기
			list = noticeRepository.findAll(page);
		} else {

			if (searchType.equals("title")) {
				// 만약 검색한 내용이 제목을 검색한 것이라면 해당 리스트를 가져오기
				list = noticeRepository.findByTitleContaining(search, page);
			} else if (searchType.equals("content")) {
				// 만약 검색한 내용이 내용을 검색한 것이라면 해당 리스트를 가져오기
				list = noticeRepository.findByContentContaining(search, page);
			} else if (searchType.equals("name")) {
				// 만약 검색한 내용이 작성자를 검색한 것이라면 해당 리스트를 가져오기
				list = noticeRepository.findByRegistNo_nameContaining(search, page);
			}
		}

		model.addAttribute("searchType", searchType);
		model.addAttribute("search", search);
		model.addAttribute("noticeList", list);

	}

	// 공지사항 게시글 삭제 기능
	@Override
	public void delete(long noticeno) {
		noticeRepository.deleteById(noticeno);

	}

	// 공지사항 게시글 내용 업데이트 기능
	@Override
	public void update(BoardNoticeDTO dto, long noticeNo) {

		// 사번으로 사원정보 조회
		EmployeesEntity emp = employeesRepository.findById(dto.getNo()).orElseThrow();

		// 업데이트 기능
		noticeRepository.save(dto.toEntityForUpdate(noticeNo, emp));
	}

	// 인덱스에 띄워줄 공지사항 게시글 내용
	@Override
	public void findListForIndex(ModelAndView modelAndView) {

		Pageable page = PageRequest.of(0, 5, Direction.DESC, "createDate");

		modelAndView.addObject("boardPage", noticeRepository.findAll(page));
		modelAndView.addObject("url", "/noticeDetail");
		modelAndView.addObject("boardNo", "noticeNo");

	}
}
