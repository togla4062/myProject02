package project.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WeatherBotController {

	//@Autowired
	//private KomoranService komoranService;
	
	@Autowired
	WeatherBotService weatherBotService;
	
	@PostMapping("/weather-bot/{order}")
	public String message(WeatherRequestDTO weatherRequestDTO,@PathVariable int order,Model model) throws Exception {
		System.out.println(">>>" + order);
		model.addAttribute("msg", weatherBotService.getAnswer(weatherRequestDTO, order));
		
		return "chatbot/weather-message";
	}
}
