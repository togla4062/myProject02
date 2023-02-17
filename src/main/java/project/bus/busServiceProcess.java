package project.bus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.bus.dto.BusResponse;
import project.bus.dto.Items;
import project.utils.openutil.OpenApiUtil;

@Service
public class busServiceProcess implements busService{
	
	//버스정보
	@Value("${bus.encoding.key}")
	private String serviceKey;

	
	
	
	@Override
	public void getBus(String areaSrch, ModelAndView mv) {
		
		System.out.println(busUtil.getBusAreaNameFromCode(areaSrch));
		
		String areaCode=busUtil.getBusAreaCode(areaSrch);

		System.out.println(">>>>>>>>>>"+areaCode);
		
		if(StringUtils.isEmpty(areaCode)) {
			System.out.println("잘못된 입력값 : "+areaSrch);
			return;
		}
		
		System.out.println("정상적인 값이 입력되었습니다 = "+areaCode);
		
		
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B551177/BusInformation/getBusInfo"); //baseUrl주소
		
		try {
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8")+"="+serviceKey);//서비스키
			urlBuilder.append("&" + URLEncoder.encode("area","UTF-8")+ "=" +URLEncoder.encode(areaCode,"UTF-8"));//검색하는 지역번호
			urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json" , "UTF-8"));//타입 지정
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String apiUrl=urlBuilder.toString();
		System.out.println(apiUrl);
		
		Map<String, String> requestHeader=new HashMap<>();
		
		requestHeader.put("Content-type", "application/json");
		
		String responseData=OpenApiUtil.request(apiUrl, requestHeader, "GET");
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			BusResponse<Items> result = objectMapper.readValue(responseData, BusResponse.class);
			mv.addObject("list",result.getResponse().getBody().getItems());
			System.out.println(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}


	
	
	
	
	
//		switch(srch) {
//			case "서울": result="1";
//				break;
//			case "경기": result="2";
//				break;
//			case "인천": result="3";
//				break;
//			case "강원": result="4";
//				break;
//			case "충청": result="5";
//				break;
//			case "경상": result="6";
//				break;
//			case "전라": result="7";
//				break;
//		}
//		
//		return result; gg_member gg_엔티티이름
		
}

