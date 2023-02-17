package project.chatbot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.utils.openutil.OpenApiUtil;
import project.weather.Item;
import project.weather.WeatherResult;

@Service
public class WeatherBotService {
	
	String[] scenario={
			"1. 조회할 지역을 입력해주세요.</br> (ex: 서울시 노원구)",
			"2. 조회할 날짜를 입력해주세요.</br> (형식:yyyymmdd, 범위:금일~익일)",
			"3. 조회할 시간을 입력해주세요.</br> (형식:hhmm, 범위:0000~2359)"};
	
	
	@Value("${data.go.kr.weather.serviceKey}")
	private String serviceKey;

	public WeatherAnswer getAnswer(WeatherRequestDTO dto, int order) {
		
		LocalDateTime today=LocalDateTime.now();
		//DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("a H:mm");
		DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		
		
		
		
		WeatherAnswer answer=WeatherAnswer.builder()
				.time(timeFormatter.format(today))
				.areaInfo(dto.getAreaInfo())
				.nx(dto.getNx())
				.ny(dto.getNy())
				.fcstDate(dto.getFcstDate())
				.fcstTime(dto.getFcstTime())
				.build();
		
		
		switch(order) {
		case 0:
			answer.setInfo("날씨예보 봇을 이용해주셔서 감사합니다.</br>아래순서에 따라 정보를 입력해주세요");
			answer.setToday(dateFormatter.format(today));
			answer.setScenario(scenario[0]);
			answer.setOrder(1);
			break;
		case 1:
			//해당하는 위치좌표확인
			//2차질문
			answer.setScenario(scenario[1]);
			answer.setOrder(2);
			answer.setAreaInfo(dto.getMessage());
			break;
		case 2:
			answer.setScenario(scenario[2]);
			answer.setOrder(3);
			answer.setFcstDate(dto.getMessage());
			break;
		case 3:
			dto.setFcstTime(dto.getMessage());
			answer.setFcstTime(dto.getMessage());
			answer.setFinalMsg(dto.getAreaInfo()+"기상예보입니다.");
			
			answer.setItems(weatherInfo(dto));
			break;
		}
			
		return answer;
	}
	
	
	
private List<Item> weatherInfo(WeatherRequestDTO dto) {
	String serviceUrl="http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
	StringBuilder urlBuilder = new StringBuilder(serviceUrl); /*URL*/
	LocalDateTime today=LocalDateTime.now();
	int hour=today.getHour();
	//baseTime 시간 "0200","0500","0800","1100","1400","1700","2000","2300"
	if( hour%3 != 2) { 
		today=LocalDateTime.now().minusHours(hour%3+1);//0=1 1=2
	}
	DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("HHmm");//hhmm
	DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyyMMdd");
	String baseDate=dateFormatter.format(today);
	String baseTime=timeFormatter.format(today);
	System.out.println(baseTime);
        try {
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" +serviceKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
            urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일발표*/
            urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*05시 발표*/
            urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(dto.getNx(), "UTF-8")); /*예보지점의 X 좌표값*/
            urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(dto.getNy(), "UTF-8")); /*예보지점의 Y 좌표값*/
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
        Map<String, String> requestHeaders=new HashMap<>();
        requestHeaders.put("Content-type", "application/json");
        
        String responseStr=OpenApiUtil.request(urlBuilder.toString(), requestHeaders, "GET");
        System.out.println(responseStr);
        
        ObjectMapper om=new ObjectMapper();
        WeatherResult result=null;
        try {
			result=om.readValue(responseStr, WeatherResult.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        System.out.println(dto.getFcstDate()+":"+dto.getFcstTime());
        List<Item> list= result.getResponse().getBody().getItems().getItem().stream()
        				.filter(i->i.getFcstDate().equals(dto.getFcstDate()) && i.getFcstTime().equals(dto.getFcstTime()))
				        .filter(i->i.getCategory().equals("POP") || i.getCategory().equals("SKY") || i.getCategory().equals("TMP"))
				        .collect(Collectors.toList());
        System.out.println(list.toString());
        System.out.println("왜 안와");
        return list;
	}
	
	

}