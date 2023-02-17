package project.weather;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.utils.openutil.OpenApiUtil;


@RestController
public class WeatherController {
	
	@Value("${data.go.kr.weather.serviceKey}")
	private String key; 
	
	@GetMapping("/weather")
	public ModelAndView weather(ModelAndView mv) {
		mv.setViewName("/weather/default");
		return mv;
	}
	
	@GetMapping("/weather/info")
	public ModelAndView weatherInfo(ModelAndView mv, String nx, String ny, String baseDate, String baseTime) throws JsonMappingException, JsonProcessingException {
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
		
        try {
        	  urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); /*Service Key*/
        	  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        	  urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("300", "UTF-8")); /*한 페이지 결과 수*/
              urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
              urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /*‘21년 6월 28일 발표*/
              urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /*06시 발표(정시단위) */
              urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
              urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
        System.out.println(">>> URL : " + urlBuilder);
        
        Map<String, String> requestHeaders=new HashMap<>();
        requestHeaders.put("Content-type", "application/json");
        
        String responseStr=OpenApiUtil.request(urlBuilder.toString(), requestHeaders, "GET");
        
        ObjectMapper om=new ObjectMapper();
        WeatherResult result =  om.readValue(responseStr, WeatherResult.class);
        List<Item> list = result.getResponse().getBody().getItems().getItem().stream()
        		//return==true일때 collect, return!=true이면 filter
        		.filter(i -> i.getCategory().equals("POP")||i.getCategory().equals("SKY")||i.getCategory().equals("TMP"))
        		.collect(Collectors.toList());
        System.out.println(">>> list : " + list);
        mv.addObject("list", list);
        mv.setViewName("weather/list");
        return mv;
        
	}
}
