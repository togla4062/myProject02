package project.chatbot;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import project.api.OpenApiUtil;

@Service
public class FindCountryWarning {

	@Value("${travel.warning.api.service.key}")
	private String key;

	public WarningInfoDTO findDataByCountryCode(String countrycode) {
		if (countrycode == null || countrycode.equals("KR")|| countrycode.equals("KP")) {
			WarningInfoDTO info = WarningInfoDTO.builder().build();
			return info;
		} else {

			StringBuilder urlBuilder = new StringBuilder(
					"http://apis.data.go.kr/1262000/TravelAlarmService2/getTravelAlarmList2"); /* URL */

			try {
				urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key); // Service Key
				urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + "10");// 페이지 당 갯수
				urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + "1"); // 페이지 넘버
				urlBuilder.append("&" + URLEncoder.encode("cond[country_iso_alp2::EQ]", "UTF-8") + "=" + countrycode); // 페이지
																														// 넘버
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			String apiUrl = urlBuilder.toString();

			Map<String, String> requestHeaders = new HashMap<>();
			requestHeaders.put("Content-type", "application/json");

			String responseData = OpenApiUtil.request(apiUrl, requestHeaders, "GET");

			JSONObject jsonRoot = new JSONObject(responseData);
			JSONObject jsonObject = new JSONObject(
					jsonRoot.get("data").toString().substring(1, jsonRoot.get("data").toString().length() - 1));

			WarningInfoDTO info = WarningInfoDTO.builder().build();

			info.setCountry_nm((String) jsonObject.get("country_nm"));
			info.setCountry_eng_nm((String) jsonObject.get("country_eng_nm"));
			info.setCountry_iso_alp2((String) jsonObject.get("country_iso_alp2"));
			info.setContinent_eng_nm((String) jsonObject.get("continent_eng_nm"));
			info.setContinent_nm((String) jsonObject.get("continent_nm"));
			if (jsonObject.get("alarm_lvl").equals(null)) {
				info.setAlarm_lvl(0);
			} else {
				info.setAlarm_lvl((int) jsonObject.get("alarm_lvl"));
			}
			if (jsonObject.get("remark").equals(null)) {
				info.setRemark("정보가 없습니다");
			} else {
				info.setRemark((String) jsonObject.get("remark"));
			}
			if (jsonObject.get("region_ty").equals(null)) {
				info.setRegion_ty("위험 지역");
			} else {
				info.setRegion_ty((String) jsonObject.get("region_ty"));
			}
			return info;
		}
	}

}
