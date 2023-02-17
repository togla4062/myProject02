package project.service.proc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import project.domain.DTO.ScheduleInsertDTO;
import project.domain.DTO.ScheduleUpdateDTO;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ScheduleEntity;
import project.domain.repository.EmployeesEntityRepository;
import project.domain.repository.ScheduleEntityRepository;
import project.service.ScheduleService;

@Service
public class ScheduleServiceProc implements ScheduleService {

	@Autowired
	EmployeesEntityRepository employeesRepository;

	@Autowired
	ScheduleEntityRepository scheduleRepository;

	// 스케줄을 데이터를 저장해 주는 기능
	@Override
	public void save(ScheduleInsertDTO dto, long empNo) {

		// 로그인한 회원 정보 조회
		EmployeesEntity emp = employeesRepository.findById(empNo).orElseThrow();

		// 조회한 회원의 스케줄 정보 저장 기능
		scheduleRepository.save(dto.toSceduleEntityForSave(emp));
	}

	// 일정 수정 기능
	@Override
	public void update(ScheduleUpdateDTO dto, long empNo) {

		EmployeesEntity emp = employeesRepository.findById(empNo).orElseThrow();

		scheduleRepository.save(dto.toEntityForUpdate(emp));
	}

	// 스케줄 정보 전체 조회후 반환해 주는 기능
	@Override
	public List<ScheduleEntity> findAll() {
		return scheduleRepository.findAll();
	}

	// 일정 삭제 기능
	@Override
	public void delete(long scheduleNo) {
		scheduleRepository.deleteById(scheduleNo);
	}

	// 로그인한 회원의 오늘 날짜에 대한 리스트 출력 기능
	@Override
	public void findAllByToday(LocalDate date, Model model, long empNo) {
		
		model.addAttribute("date", date);
		model.addAttribute("todaySchedule",findAllByDate(date,model,empNo));

	}	
	// 로그인한 회원의 오늘 날짜에 대한 리스트 5개 출력 기능(인덱스페이지용)
	@Override
	public void findAllByTodayForIndex(LocalDate now, Model model, long empNo) {
		
		int size = findAllByDate(now,model,empNo).size();
		
		if(size>4) {
		model.addAttribute("todaySchedule",findAllByDate(now,model,empNo).subList(0, 4));
		}else {
		model.addAttribute("todaySchedule",findAllByDate(now,model,empNo).subList(0, size));
		}
		model.addAttribute("today", now);
	}
	
	// 로그인한 회원의 내일 날짜에 대한 리스트 5개 출력 기능(인덱스페이지용)
	@Override
	public void findAllByTomorrowForIndex(LocalDate plusDays, Model model, long empNo) {

		int size = findAllByDate(plusDays,model,empNo).size();
		
		if(size>4) {
		model.addAttribute("tomorrowSchedule",findAllByDate(plusDays,model,empNo).subList(0, 4));
		}else {
		model.addAttribute("tomorrowSchedule",findAllByDate(plusDays,model,empNo).subList(0, size));
		}
		model.addAttribute("tomorrow", plusDays);
	}


	/**
	 * 두 날짜 사이의 모든 날짜를 받아오는 기능(시작날짜부터 종료일 전날까지 리스트로 받아짐)
	 * 
	 * @param startDate 시작일(LoclaDate 타입)
	 * @param endDate   종료일(LoclaDate 타입)
	 * @return 두 날짜 사이의 모든 날짜 리스트를 반환
	 */
	public List<LocalDate> getDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
		return startDate.datesUntil(endDate).collect(Collectors.toList());
	}
	
	/** 특정 날짜를 전달받아 그날 해당 회원의 일정 정보를 모두 조회해 주는 매서드
	 * 
	 * @param date	해당 날짜
	 * @param model	데이터를 전송해 주는 역할
	 * @param empNo	사원번호
	 * @return	해당 날짜의 해당사원이 해야할 일정 정보 리스트
	 */
	private List<ScheduleEntity> findAllByDate(LocalDate date, Model model, long empNo) {
		// 사원 정보 조회
		EmployeesEntity emp = employeesRepository.findById(empNo).orElseThrow();
		// 사원정보로 일정 리스트 조회
		List<ScheduleEntity> list = scheduleRepository.findByemployeeNo(emp);

		// 리스트 페이지에 보내줄 정보 리스트
		List<ScheduleEntity> todayList = new ArrayList<>();

		for (ScheduleEntity schedule : list) {

			// 스케줄 시작일이 해당 날짜인 경우
			if (schedule.getStartDate().isEqual(date)) {
				todayList.add(schedule);
			}

			// 스케줄 종료일이 해당 날짜인 경우
			else if (schedule.getEndDate().isEqual(date)) {
				todayList.add(schedule);
			}

			// 스케줄 시작일과 종료일 사이에 해당 날짜가 있는 경우
			else {

				List<LocalDate> dateList = getDatesBetweenTwoDates(schedule.getStartDate(), schedule.getEndDate());

				for (LocalDate betweenDate : dateList) {

					if (betweenDate.isEqual(date)) {
						todayList.add(schedule);
					}
				}
			}
		}
		return todayList;
	}

}
