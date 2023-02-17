package project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.ui.Model;

import project.domain.DTO.ScheduleInsertDTO;
import project.domain.DTO.ScheduleUpdateDTO;
import project.domain.entity.ScheduleEntity;

public interface ScheduleService {

	void save(ScheduleInsertDTO dto, long empNo);

	List<ScheduleEntity> findAll();

	void findAllByToday(LocalDate date, Model model, long empNo);

	void delete(long scheduleNo);

	void update(ScheduleUpdateDTO dto, long empNo);

	void findAllByTodayForIndex(LocalDate now, Model model, long empNo);

	void findAllByTomorrowForIndex(LocalDate plusDays, Model model, long empNo);

}
