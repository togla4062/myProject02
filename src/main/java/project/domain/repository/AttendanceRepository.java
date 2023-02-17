package project.domain.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import project.domain.entity.DailyWorkingHoursEntity;
import project.domain.entity.EmployeesEntity;

public interface AttendanceRepository extends JpaRepository<DailyWorkingHoursEntity, Long> {
	List<DailyWorkingHoursEntity> findByEmployeeNo(EmployeesEntity emp);
	
	Page<DailyWorkingHoursEntity> findByEmployee_noOrderByDateDesc(long no, Pageable pageable);

	Optional<DailyWorkingHoursEntity> findByEmployee_no(long no);

	Page<DailyWorkingHoursEntity> findByEmployee_noAndDateBetweenOrderByDateDesc(long no, LocalDate dateStart,
			LocalDate dateEnd, Pageable pageable);

	Optional<DailyWorkingHoursEntity> findByEmployee_noAndDateBetween(long no, LocalDate today, LocalDate tomorrow);

	List<DailyWorkingHoursEntity> findByDateBetweenAndEmployee_no(LocalDate today, LocalDate tomorrow, long no);

	Optional<DailyWorkingHoursEntity> findByEmployee_noAndClockInBetween(long no, LocalDate today, LocalDate tomorrow);

}
