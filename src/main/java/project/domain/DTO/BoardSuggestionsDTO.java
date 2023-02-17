package project.domain.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import project.domain.entity.BoardSuggestionsEntity;
import project.domain.entity.EmployeesEntity;

@Data
public class BoardSuggestionsDTO {
	
	//사번
	private long no;
	
	//제목
	private String title;
	
	//내용
	private String content;
	
	/**
	 * 데이터 Save 용 편의 메서드
	 * 
	 * @param registNo  : (EmployeesEntity타입) 사원 번호
	 * @return Save를 수행할 BoardSuggestionsEntity 타입 반환
	 */
	public BoardSuggestionsEntity toEntityForSave(EmployeesEntity registNo) {
		return BoardSuggestionsEntity.builder()
				.title(title).content(content).registNo(registNo)
				.build();
	}
	
	/**
	 * 데이터 Update 용 편의 메서드
	 * 
	 * @param suggestNo : (long타입) 게시글 번호
	 * @param registNo : (EmployeesEntity타입) 사원 번호
	 * @return Update를 수행할 BoardSuggestionsEntity 타입 반환
	 */
	public BoardSuggestionsEntity toEntityForUpdate(long suggestNo, EmployeesEntity registNo) {
		LocalDateTime today = LocalDateTime.now();
		
		return BoardSuggestionsEntity.builder()
				.suggestNo(suggestNo).title(title).content(content).registNo(registNo).createDate(today)
				.build();
	}
}
