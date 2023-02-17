package project.service.proc;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import project.domain.DTO.EmployeesDetailDTO;
import project.domain.DTO.EmployeesDetailUpdateDTO;
import project.domain.DTO.EmployeesUpdateDTO;
import project.domain.DTO.PageDTO;
import project.domain.entity.DepartmentsEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.repository.DepartmentsEntityRepository;
import project.domain.repository.EmployeesEntityRepository;
import project.domain.repository.PersonnelEvaRepository;
import project.service.OrganizationChartService;

@Service
public class OrganizationChartServiceProcess implements OrganizationChartService {
	/* 230113 한아 작성 */
	
	@Autowired
	EmployeesEntityRepository employeesRepo;
	
	@Autowired
	DepartmentsEntityRepository departmentsRepo;

	@Autowired
	PersonnelEvaRepository personnelEvaRepo;


	//전체 리스트 페이징
	@Override
	public void listForAjax(ModelAndView mv, int page) {
		int rowTotal = employeesRepo.countAllByDeleteStatus(false); //총 사원 수
		System.out.println("총 사원 수 : "+rowTotal);
		int size = 10; //한 페이지에 보여줄 게시글 수
		Pageable pa = PageRequest.of(page-1, size);
		Page<EmployeesEntity> result = employeesRepo.findAllByDeleteStatusOrderByPositionRank(false, pa);
		//페이지정보
		PageDTO pageInfo = PageDTO.getInstance(page, rowTotal, size, 5);
		
		mv.addObject("list1", result.getContent());
		mv.addObject("pi", pageInfo);
	}
	
	//퇴직처리된 사원 조회
	@Override
	public void findAllByDeleteStatusTrue(Model model) {
		model.addAttribute("list1", employeesRepo.findAllByDeleteStatusOrderByPositionRank(true));
		
	}

	//organizationChart 부서별 리스트 조회
	@Override
	public void findAllByDepartmentNo(Model model, Long department) {
		System.out.println("department : "+department);
		
		List<EmployeesEntity> list2 = employeesRepo.findAllByDepartmentNoDepartmentNoAndDeleteStatusOrderByPositionRank(department, false);
		model.addAttribute("list2", list2);
		
		boolean nullcheck = false;
		if(list2.isEmpty()) {
			nullcheck = true;
		}
		model.addAttribute("nullcheck", nullcheck);
		
	}

	//조직도 디테일 페이지
	@Override
	public void findById(Model model, Long no) {
		model.addAttribute("list", employeesRepo.findById(no)
				.map(EmployeesDetailDTO::new)
				.orElseThrow());
	}
	//조직도 디테일 수정 페이지
	@Override
	public void findByIdEditMode(Model model, Long no) {
		model.addAttribute("list", employeesRepo.findById(no)
				.map(EmployeesDetailUpdateDTO::new)
				.orElseThrow());
	}
	//사원 정보 수정
	@Transactional
	@Override
	public void editmode(Long no, EmployeesUpdateDTO dto) {
		employeesRepo.findById(no).map(entity->entity.updateInfo(dto));
		
	}
	//familyTree 대표이사 정보
	@Override
	public void findCEO(Model model) {
		EmployeesEntity ceo = employeesRepo.findByPositionRank(0L);
		model.addAttribute("ceo", ceo);
		model.addAttribute("ceoImg", ceo.getImageNo().getUrl());
	}
	//eva
	@Override
	public void findAllList(Model model) {

	}
	//familyTree 부서장 정보
	@Override
	public void findDepartmentHead(Model model) {
		List<DepartmentsEntity> dent = departmentsRepo.findAll();
		List<EmployeesEntity> eent = employeesRepo.findAll();
		
		List<String> str = new ArrayList<>(); //사용 전에 초기화 꼬옥 시켜주자(자리 만들어주기)
		List<Long> headinfo = new ArrayList<>(); //사용 전에 초기화 꼬옥 시켜주자(자리 만들어주기)
		
		for(DepartmentsEntity d : dent) {//부서장 이름과 동일한 이름을 가진 사원 이미지url 가져오기
			for(EmployeesEntity e : eent) {
				if(d.getDepartmentHead().equals(e.getName())) {
					System.err.println(d.getDepartmentHead());
					headinfo.add(e.getNo());
					System.out.println(e.getNo());
					str.add(e.getImageNo().getUrl());
				}
			}
			if(d.getDepartmentHead().equals("미정")){
				str.add("/image/icon/vacant.png");
				headinfo.add(0L);
			}
		}
		model.addAttribute("images", str);
		model.addAttribute("no", headinfo);

	}

  @Override
	public void treelist(Model model, Long no) {
		model.addAttribute("treelist", employeesRepo.findAllByDepartmentNoDepartmentNoAndDeleteStatusOrderByPositionRank(no, false));

	}

  //검색하기
  @Override
  public void findAllList(int pageNum, String search, String searchType, Model model) {
	//한 페이지에 표현해줄 리스트 갯수
	int pageSize = 10;
	
	// 리스트 페이지에 출력해줄 데이터리스트
	Page<EmployeesEntity> list = null;
	
	// 페이징기능(페이지인덱스번호,페이지 사이즈,정렬방식,정렬할 컬럼이름)
	Pageable page = PageRequest.of(pageNum - 1, pageSize, Direction.ASC, "positionRank");
	
	if (search.equals("전체")) {
		// 만약 검색한 내용이 없다면 전체 리스트 정보 가져오기
		list = employeesRepo.findAll(page);
	} else {
	
		if (searchType.equals("name")) {
			// 만약 검색한 내용이 이름을 검색한 것이라면 해당 리스트를 가져오기
			list = employeesRepo.findByNameContaining(search, page);
		}else if(searchType.equals("email")) {
			// 만약 검색한 내용이 이메일을 검색한 것이라면 해당 리스트를 가져오기
			list = employeesRepo.findByEmailContaining(search, page);
		}
	}
	model.addAttribute("searchType", searchType);
	model.addAttribute("search", search);
	model.addAttribute("searchlist", list);
  }

  @Override
  public void findAllByDeleteStatusFalse(Model model, Pageable pageable) { //수민 인사평가 리스트
	  Page<EmployeesEntity> pageResult = employeesRepo.findAllByDeleteStatusOrderByPositionRank(false, pageable);
		model.addAttribute("list1",pageResult);
		model.addAttribute("pageNum", pageResult.getNumber()+1 ); // 현재 페이지 번호 0번부터 시작하기 때문에 +1
		model.addAttribute("pageSize", pageResult.getSize()); // 한 페이지의 게시글 수
		model.addAttribute("pageTotal", pageResult.getTotalPages()); // 총 페이지 수
		model.addAttribute("endPage", 10); // 페이징 10개까지 보여줄거야
  }
	
}
