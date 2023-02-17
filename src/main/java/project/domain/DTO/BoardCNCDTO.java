package project.domain.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import project.domain.entity.BoardCNCEntity;
import project.domain.entity.EmployeesEntity;

@Data
public class BoardCNCDTO {

	private long no;

	private String title;

	private String content;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate eventDate;
	
	public BoardCNCEntity toEntityForSave(EmployeesEntity registNo) {
		return BoardCNCEntity.builder()
				.title(title).content(content).registNo(registNo).eventDate(eventDate)
				.build();
	}

	
	public BoardCNCEntity toEntityForUpdate(long cncNo, EmployeesEntity registNo) {
		LocalDateTime today = LocalDateTime.now();
		return BoardCNCEntity.builder()
				.cncNo(cncNo).title(title).content(content).registNo(registNo).eventDate(eventDate).createDate(today)
				.build();
	}

	
	/*  public BoardCNCDTO(BoardCNCEntity ent) {
	  
	  this.no = ent.getCncNo(); 
	  this.title = ent.getTitle();
	  this.content = ent.getContent();
	  this.eventDate = ent.getEventDate(); }
	 */
}
