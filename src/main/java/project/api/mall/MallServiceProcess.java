package project.api.mall;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class MallServiceProcess implements MallService {

	@Override
	public void getjson(Model model) {
		String url = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/myr/krw.json";  // example url which return json data
		String exchangeInfo = JsonReader.callURL(url);
		
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(exchangeInfo);
		JsonElement date = obj.get("date");
		JsonElement rate = obj.get("krw");
		String today = date.toString().replaceAll("\"", "");
		
		//System.out.println("날짜"+ today);
		model.addAttribute("today", today);
		model.addAttribute("rate", rate);
		
	}


}
