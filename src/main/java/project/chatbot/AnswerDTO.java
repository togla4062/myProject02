package project.chatbot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDTO {
	
	private long no;
	private String content;
	private String keyword;//name
	
	private PhoneInfo phone;
	
	private WarningInfoDTO warningInfo;
	
	public AnswerDTO phone(PhoneInfo phone){
		this.phone=phone;
		return this;
	}

}
