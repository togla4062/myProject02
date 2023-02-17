package project.api.dto;

import lombok.Data;

@Data
public class BusRouteListResponseDTO {
	
	private ComMsgHeader comMsgHeader;
	private MsgHeader msgHeader;
	private MsgBody msgBody;

}
