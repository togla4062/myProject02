package project.chatbot;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WarningInfoDTO {
	
	private String country_nm;
	
	private String country_eng_nm;
	
	private String country_iso_alp2;
	
	private String continent_eng_nm;
	
	private String continent_nm;
	
	private int alarm_lvl;
	
	private String remark;
	
	private String region_ty;
	
}
