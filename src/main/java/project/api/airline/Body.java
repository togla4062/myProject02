package project.api.airline;

import lombok.Data;

@Data
public class Body {
	
	private Items items;
	private String numOfRows;
	private String pageNo;
	private String totalCount;

}
