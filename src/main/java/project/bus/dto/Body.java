package project.bus.dto;

import java.util.List;

import lombok.Data;

@Data
public class Body<T> {
	private int numOfRows;
	private int pageNo;
	private int totalCount;
	private List<T> items;
}
