package project.domain.DTO;

import lombok.Data;

@Data
public class TodayAirportInfoDTO {

	// 항공편명
	private String airFln;
	// 항공사 영문
	private String airlineEnglish;
	// 항공사 국문
	private String airlineKorean;
	// 기준공항코드
	private String airport;
	// 도착공항 영문
	private String arrivedEng;
	// 도착공항 국문
	private String arrivedKor;
	// 출발공항 영문
	private String boardingEng;
	// 출발공항 국문
	private String boardingKor;
	// 운항구간코드
	private String city;
	// 변경시간
	private String etd;
	// 탑승구
	private String gate;
	// 도착 출발 분류
	private String io;
	// 국내 국제 구분
	private String line;
	// 항공편 상태 영문
	private String rmkEng;
	// 항공편 상태 국문
	private String rmkKor;
	// 예정시간
	private String std;

}
