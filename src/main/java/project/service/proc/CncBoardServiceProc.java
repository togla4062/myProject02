package project.service.proc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import project.domain.DTO.AttendanceMyListDTO;
import project.domain.DTO.BoardCNCDTO;
import project.domain.entity.BoardCNCEntity;
import project.domain.entity.DailyWorkingHoursEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.repository.AttendanceRepository;
import project.domain.repository.BoardCNCEntityRepository;
import project.domain.repository.EmployeesEntityRepository;
import project.service.CNCBoardService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CncBoardServiceProc implements CNCBoardService {

	@Autowired
	private AttendanceRepository attRepo;
	
	@Autowired
	BoardCNCEntityRepository CNCRepository;

	@Autowired
	EmployeesEntityRepository employeesRepository;

	//경조사 게시판 조회 및 검색!
	@Override
	public void findAllList(int pageNum, String search, String searchType, Model model) {

		int pageSize = 10;

		Page<BoardCNCEntity> list = null;

		Pageable page = PageRequest.of(pageNum - 1, pageSize, Direction.DESC, "eventDate");

		if (search.equals("전체")){
			list = CNCRepository.findAll(page);
		} else {

			if (searchType.equals("title")) {
				list = CNCRepository.findByTitleContaining(search, page);
			}else if(searchType.equals("content")) {
				list = CNCRepository.findByContentContaining(search, page);
			}else if(searchType.equals("name")) {
				list = CNCRepository.findByRegistNo_nameContaining(search, page);
			}
		}

		/*
		 * // false : 조회한 데이터가 있음 // true : 조회한 데이터가 없음 boolean nullcheck = false; //
		 * 조회한 데이터의 유무를 확인하는 변수
		 * 
		 * if (list.isEmpty()) { nullcheck = true; }
		 */
		/* model.addAttribute("nullcheck", nullcheck); */
		model.addAttribute("searchType", searchType);
		model.addAttribute("search", search);
		model.addAttribute("cncList", list);
		model.addAttribute("today", LocalDate.now()); //cncList 경조사 게시판에 경조사일 날짜를 비교가능하게해줌 (지난 경조사일 회색처리)
	}
	//경조사 저장!
	@Override
	public void save(BoardCNCDTO cdto) {
		/* BoardCNCEntity entity=BoardEntity.builder()
				.title(dto.getTitle()).content(dto.getContent())
				.member(MemberEntity.builder().no(dto.getno()).build())
				.build();
		
		repository.save(entity); */
		
		// 사번으로 사원정보 조회
		EmployeesEntity emp = employeesRepository.findById(cdto.getNo()).orElseThrow();

		// 경조사 테이블에 저장
		CNCRepository.save(cdto.toEntityForSave(emp));
	}

	@Override
	public void detail(long cncNo, Model model) {

		BoardCNCEntity entityData = CNCRepository.findById(cncNo).orElseThrow();
		// BoardCNCDTO date= new BoardCNCDTO(entityData);
		// BoardCNCDTO date=new BoardCNCDTO(1,"titleasdf","contentasdf");

		// model.addAttribute("cncDetail", date);
		model.addAttribute("cncDetail", entityData);
	}

	/* 230113 문대현 생성 */
	// 모든 경조사 데이터 조회 한 후에 반환해 주는 기능
	@Override
	public List<BoardCNCEntity> findAll() {
		return CNCRepository.findAll();
	}

	// 게시글 수정
	@Override
	public void update(BoardCNCDTO cdto, long cncNo) {
		// 사번으로 사원정보 조회
		EmployeesEntity emp = employeesRepository.findById(cdto.getNo()).orElseThrow();

		// 업데이트 기능
		CNCRepository.save(cdto.toEntityForUpdate(cncNo, emp));
	}

	//게시글 삭제
	@Override
	public void delete(long cncNo) {
			// 게시글 번호로 게시글 정보 조회
			BoardCNCEntity suggestions = CNCRepository.findById(cncNo).orElseThrow();
			// 해당 게시글을 삭제
			CNCRepository.deleteById(cncNo);
		}
	
	/*
    @Transactional
    public Page<BoardCNCEntity> search(String keyword, Pageable pageable) {
        Page<BoardCNCEntity> postsList = CNCRepository.findByTitleContaining(keyword, pageable);
        return postsList;
    }
    
	@Override
	public void getListAll(int page, Model model) {
		int size=10;
		Sort sort=Sort.by(Direction.DESC, "cncNo");
		Pageable pageable=PageRequest.of(page-1, size, sort);
		Page<BoardCNCEntity> result=CNCRepository.findAll(pageable);
		model.addAttribute("p", result);
		model.addAttribute("list", result.stream()
				.map(BoardCNCDTO::new)//Entity->DTO
				.collect(Collectors.toList()));
	
	}*/

	// 인덱스에 띄워줄 경조사 게시글 내용
	@Override
	public void findListForIndex(ModelAndView modelAndView) {
		Pageable page = PageRequest.of(0, 5, Direction.DESC, "createDate");
		List<BoardCNCEntity> list= CNCRepository.findByEventDateAfter(LocalDate.now().minusDays(1));
		if(list.size()>5) {
			list= list.subList(0, 5);
		}else {
			list = list.subList(0, list.size());
		}
		modelAndView.addObject("boardPage", list);
		modelAndView.addObject("url", "/cncDetail" );
		modelAndView.addObject("boardNo", "cncNo" );

	}

}

/*
 * //삭제처리!
 * 
 * @Override public void delete(long cncNo) { CNCRepository.deleteById(cncNo); }
 * 
 * //수정처리!
 * 
 * @Transactional // map()에서 수정한정보가 적용되어 update 쿼리가 실행됨
 * 
 * @Override public void updateProc(long cncNo, BoardCNCDTO cdto) { //수정처리 대상은
 * bno(pk), 수정할데이터 dto:제목,내용 //1.대상의 존재여부 Optional<BoardCNCEntity> result=
 * CNCRepository.findById(cncNo); //2.존재하면 수정 if(result.isPresent()) {
 * BoardCNCEntity entity=result.get(); //수정하기위한 편의메서드 아니면 setter메서드 이용하세요
 * entity.updateProc(cdto); ///원본-업데이트 반영 CNCRepository.save(entity);//이미 존재하는
 * Pk이면 수정반영됩니다. } }
 */