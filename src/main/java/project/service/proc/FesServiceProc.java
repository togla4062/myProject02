package project.service.proc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.domain.DTO.PageDTO;
import project.domain.DTO.fes.FestivalBaseInfo;
import project.domain.DTO.fes.Source;
import project.service.FesService;
import project.utils.OpenApiUtil;

@Service
public class FesServiceProc implements FesService {

	@Value("${data.go.kr.fes.key}")
	private String key;
	 

	
	@Override
	public void getFirstResult(String pageNo,ModelAndView mv) {
		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6290000/festivalbaseinfo/getfestivalbaseinfo"); /*URL*/
	        try {
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); /*Service Key*/
				//urlBuilder.append("&" + URLEncoder.encode("festivalNm","UTF-8") + "=" + URLEncoder.encode("festivalNm", "UTF-8")); /*축제명*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*한 페이지 결과 수*/
		        urlBuilder.append("&" + URLEncoder.encode("pageNo" ,"UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호*/
		        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 받을 데이터 형식*/
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        
	        String apiURL = urlBuilder.toString(); // 요청 주소값
	        System.out.println(">>>" + apiURL);
	        
	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("Content-type", "application/json");
	        String responseData = OpenApiUtil.request(apiURL, requestHeaders, "GET");
	        System.out.println(">>>>" + responseData);
	        ObjectMapper objectMapper=new ObjectMapper();
	        Source response=null;
	        try {
	        	response=objectMapper.readValue(responseData, Source.class);
	        	System.out.println(">>>>><<" + response);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	       
	        mv.addObject("page", response.getHeader());
	        mv.addObject("list", response.getFestivalBaseInfo().stream().collect(Collectors.toList())) ;
	        
	}

	@Override
	public void getSearchResult(String pageNo, String name, ModelAndView mv) {
		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6290000/festivalbaseinfo/getfestivalbaseinfo"); /*URL*/
	        try {
				urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); /*Service Key*/
				urlBuilder.append("&" + URLEncoder.encode("festivalNm","UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")); /*축제명*/
				urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("5", "UTF-8")); /*한 페이지 결과 수*/
		        urlBuilder.append("&" + URLEncoder.encode("pageNo" ,"UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*페이지 번호*/
		        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 받을 데이터 형식*/
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        
	        String apiURL = urlBuilder.toString(); // 요청 주소값
	        System.out.println(">>>" + apiURL);
	        
	        Map<String, String> requestHeaders = new HashMap<>();
	        requestHeaders.put("Content-type", "application/json");
	        String responseData = OpenApiUtil.request(apiURL, requestHeaders, "GET");
	        System.out.println(">>>>" + responseData);
	        ObjectMapper objectMapper=new ObjectMapper();
	        Source response=null;
	        try {
	        	response=objectMapper.readValue(responseData, Source.class);
	        	System.out.println(">>>>><<" + response);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
	       
	        mv.addObject("page", response.getHeader());
	        mv.addObject("list", response.getFestivalBaseInfo().stream().collect(Collectors.toList()));
	        
		
	}

}
