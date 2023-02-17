package project.bus.map.dto;

import lombok.Data;

@Data
public class ArriveRequestDTO {
	private String stId;
	private String busRouteId;
	private String ord;	
}
