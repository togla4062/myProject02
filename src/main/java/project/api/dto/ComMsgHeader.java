package project.api.dto;

import lombok.Data;

@Data
public class ComMsgHeader {
	
	private String responseTime;
	private String requestMsgID;
	private String responseMsgID;
	private String returnCode;
	private String errMsg;
	private String successYN;

}
