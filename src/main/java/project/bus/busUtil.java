package project.bus;

import java.util.HashMap;

public class busUtil {
	
		//사전같은 역할
		//해당클래스의 인스턴스 작성시 생성자내부의 처리를 반드시 실행
		private static HashMap<String, String> areaCodeMap = new HashMap<String, String>();
		static {
				areaCodeMap.put("서울", "1");
				areaCodeMap.put("경기", "2");
				areaCodeMap.put("인천", "3");
				areaCodeMap.put("강원", "4");
				areaCodeMap.put("충청", "5");
				areaCodeMap.put("경상", "6");
				areaCodeMap.put("전라", "7");		
				}
		/*
		 * api에서 제공된 area정보 (1:서울, 2:경기, 3:인천, 4:강원, 5:충청, 6:경상, 7:전라)를 
		 * 입력된 값(문자)으로 area(숫자)정보를 얻을 수 있다
		 */
		public static String getBusAreaCode(String areaName) {
			
			//경기'도',충청'도'같이 '도'를 붙여검색하는 경우도 있기때문에 replace를 통해 도="" & " "공백을 "" 으로 적용시켜서 예외처리
			return areaCodeMap.get(areaName.replace("도" , "").replaceAll(" ",""));
		}
		
		//hashMap을 이용한 key값 확인해보기(혼자 공부) 프로젝트랑 상관없음
		public static String getBusAreaNameFromCode(String areaCode) {
			
			String result = "";
			
			for (String key : areaCodeMap.keySet()) {
				if (areaCodeMap.get(key).equals(areaCode)) {
					result = key;
				}
			}
			return result;
			
		}
}
