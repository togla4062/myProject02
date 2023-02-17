package project.domain.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import project.domain.entity.BoardSuggestionsEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.entity.ReplySuggestionsEntity;

@Data
public class ReplySuggestionsDTO {
	
	//사번
	private long registNo;
	
	//게시글 번호
	private long suggestNo;
	
	//제목
	private String title;
	
	//내용
	private String content;
	
	
	/**
	 * 데이터 Save 용 편의 메서드
	 * 
	 * @param registNo : (BoardSuggestionsEntity타입) 게시글 번호
	 * @param suggestNo : (EmployeesEntity타입) 사원 번호
	 * @return Save를 수행할 ReplySuggestionsEntity 타입 반환
	 */
	public ReplySuggestionsEntity toEntityForSave(EmployeesEntity registNo, BoardSuggestionsEntity suggestNo) {
		return ReplySuggestionsEntity.builder()
				.title(title).content(content).registNo(registNo).suggestNo(suggestNo)
				.build();
	}
	
	/**
	 * 데이터 Update 용 편의 메서드
	 * 
	 * @param replySuggestNo : (long 타입) 댓글 번호
	 * @param registNo : (EmployeesEntity타입) 사원 번호
	 * @param suggestNo : (BoardSuggestionsEntity타입) 게시글 번호
	 * @return Update를 수행할 ReplySuggestionsEntity 타입 반환
	 */
	public ReplySuggestionsEntity toEntityForUpdate(long replySuggestNo, EmployeesEntity registNo, BoardSuggestionsEntity suggestNo) {
		
		//업데이트를 수행한 날짜 정보
		LocalDateTime today = LocalDateTime.now();
		
		return ReplySuggestionsEntity.builder()
				.replySuggestNo(replySuggestNo).title(title).content(content).registNo(registNo).suggestNo(suggestNo).createDate(today)
				.build();
	}
}
