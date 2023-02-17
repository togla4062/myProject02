package project.service.proc;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import project.domain.DTO.BoardNoticeDTO;

public class NoticeBoardValidator implements Validator{

	//Validator가 제공된 Class의 인스턴스 clazz를 유효성 검사를 할 수 있는가???
	@Override
	public boolean supports(Class<?> clazz) {
		//System.out.println("supports ("+ clazz.getName()+")");
		
		//검증할 객체의 클래스 타입인지 확인 : BoardNoticeDTO = clazz; 가능 여부
		return BoardNoticeDTO.class.isAssignableFrom(clazz);
		
	}
	//주어진 객체에 유효성 검사를 하고 
	//유효성 검사에 오류가 있는 경우 주어진 객체에 이 오류들을 errors에 등록한다.
	@Override
	public void validate(Object target, Errors errors) {
		//System.out.println("validate()");
		
		BoardNoticeDTO dto = (BoardNoticeDTO) target;
		
		//글 제목 한글자라도 적었는지 확인
		String title = dto.getTitle();
		if(title == null || title.trim().isEmpty()) {
			System.out.println("title 오류 : 반드시 한글자라도 입력해야 합니다.");
			errors.rejectValue("title", "emptyTitle");
		}
		
		//글 내용 한글자라도 적었는지 확인
		String content = dto.getContent();
		if(content == null || content.trim().isEmpty()) {
			System.out.println("content 오류: 반드시 한글자라도 입력해야 합니다.");
			errors.rejectValue("content", "emptyContent");
		}
	}

}
