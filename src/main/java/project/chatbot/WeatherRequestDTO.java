package project.chatbot;

import lombok.Data;

@Data
public class WeatherRequestDTO {

	private String message;
	private String areaInfo;
	private String nx;
	private String ny;
	private String fcstDate;
	private String fcstTime;
}
