package project.chatbot;

import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.weather.Item;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherAnswer {
	
	private String info;//안내메세지
	
	private String scenario;//질문
	
	private int order;
	
	private String today;//날짜, 안녕인 경우만 날짜정보 생성
	
	private String time;//시간
	
	private String areaInfo;
	private String nx;
	private String ny;
	private String fcstDate;
	private String fcstTime;
	
	private String finalMsg;
	
	@Builder.Default
	private List<Item> items= new ArrayList<>();


}
