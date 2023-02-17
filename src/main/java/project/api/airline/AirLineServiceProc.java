package project.api.airline;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.api.OpenApiUtil;

@Service
public class AirLineServiceProc implements AirlineService {
	
	@Value("${data.go.kr.key}")
	private String key;

	@Override
	public void getAirlineInfo(ModelAndView mv, String schDate, long pageNo, String schDeptCityCode, String schArrvCityCode) {
		
	        StringBuilder urlBuilder = new StringBuilder("http://openapi.airport.co.kr/service/rest/FlightScheduleList/getDflightScheduleList"); /*URL*/
	        
	        try {
	        	urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+key); /*Service Key*/
		        urlBuilder.append("&" + URLEncoder.encode("schDate","UTF-8") + "=" + URLEncoder.encode(schDate, "UTF-8")); /*검색일자*/
		        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + pageNo); /*페이지 넘버*/
		        urlBuilder.append("&" + URLEncoder.encode("schDeptCityCode","UTF-8") + "=" + URLEncoder.encode(schDeptCityCode, "UTF-8")); /*도착 도시 코드*/
		        urlBuilder.append("&" + URLEncoder.encode("schArrvCityCode","UTF-8") + "=" + URLEncoder.encode(schArrvCityCode, "UTF-8")); /*출항 도시 코드*/
		        //urlBuilder.append("&" + URLEncoder.encode("schAirLine","UTF-8") + "=" + URLEncoder.encode("AB", "UTF-8")); /*항공편 코드*/
	        	//urlBuilder.append("&" + URLEncoder.encode("schFlightNum","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*항공편 넘버*/
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
	        String apiUrl = urlBuilder.toString();
	        
	        Map<String, String> requestHeaders=new HashMap<>();
	        requestHeaders.put("Content-type", "application/json");
	        
	        String responseData=OpenApiUtil.request(apiUrl, requestHeaders, "GET");
	        
	        JSONObject jsonObject = XML.toJSONObject(responseData);
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
				AirlineDTO ald = objectMapper.readValue(jsonObject.toString(), AirlineDTO.class);
				mv.addObject("ald",ald.getResponse().getBody().getItems().getItem());
				System.out.println(ald);
				
				
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}

}
