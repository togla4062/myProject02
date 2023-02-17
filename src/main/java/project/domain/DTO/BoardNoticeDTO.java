package project.domain.DTO;

import java.time.LocalDateTime;

import lombok.Data;
import project.domain.entity.BoardNoticeEntity;
import project.domain.entity.EmployeesEntity;

@Data
public class BoardNoticeDTO {
	
	
	private long no; //사원번호
	
	private String title; //글 제목
	
	private String content;// 내용

	
		// 저장 메서드
	public BoardNoticeEntity toEntityForSave(EmployeesEntity registNo) {
		return BoardNoticeEntity.builder()
			.title(title).content(content).registNo(registNo)
			.build();
}

	//업데이트 메서드
	public BoardNoticeEntity toEntityForUpdate(long noticeNo, EmployeesEntity registNo) {
		LocalDateTime today = LocalDateTime.now();
		return BoardNoticeEntity.builder()
				.noticeNo(noticeNo).title(title).content(content).registNo(registNo).createDate(today)
				.build();
	}


	}

