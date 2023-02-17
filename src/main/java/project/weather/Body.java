package project.weather;

import lombok.Data;

@Data
public class Body {
	
	private String dataType;
	private Items items;
	private int pageNo;
	private int numOfRows;
	private int totalCount;
	
}
