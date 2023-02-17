package project.chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelpBotController {

	@Autowired
	private KomoranService komoranService;

	@PostMapping("/hbot")
	public String message(String message, Model model) throws Exception {
		
		if (komoranService.AnalyzeType(message).equals("위험 경보 조회")) {
			Thread.sleep(1000);//1초 지연(직접 타이핑 해 주는 것과 같은 감성적인 영역 삭제해도 무방)
			model.addAttribute("msg", komoranService.WarningMessege(message, model));
			return "chatbot/bot-message-warning";
		} else {
			model.addAttribute("msg", komoranService.nlpAnalyze(message));
			return "chatbot/bot-message";
		}
	}
}
