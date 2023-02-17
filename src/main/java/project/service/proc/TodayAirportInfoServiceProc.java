package project.service.proc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import project.domain.DTO.TodayAirportInfoDTO;
import project.service.TodayAirportInfoService;

@Service
public class TodayAirportInfoServiceProc implements TodayAirportInfoService {
	
	@Override
	public void apiService(String url, ModelAndView modelAndView) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		String pageNo = null;
		String totalCount = null;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(url);
			NodeList list = document.getElementsByTagName("item");
			NodeList page = document.getDocumentElement().getLastChild().getChildNodes();

			for (int i = 0; i < page.getLength(); i++) {
				Node pageval = page.item(i);
				String value = pageval.getNodeName();
				if (value.equals("pageNo"))
					pageNo = pageval.getTextContent();
				if (value.equals("totalCount"))
					totalCount = pageval.getTextContent();
			}

			List<TodayAirportInfoDTO> dto = new ArrayList<>();

			for (int i = 0; i < list.getLength(); i++) {
				Node family = list.item(i);
				NodeList cList = family.getChildNodes();
				TodayAirportInfoDTO d = new TodayAirportInfoDTO();
				for (int j = 0; j < cList.getLength(); j++) {
					Node item = cList.item(j);
					String value = item.getNodeName();
					if (value.equals("#text"))
						continue;
					if (value.equals("airFln"))
						d.setAirFln(item.getTextContent());
					if (value.equals("airlineEnglish"))
						d.setAirlineEnglish(item.getTextContent());
					if (value.equals("airlineKorean"))
						d.setAirlineKorean(item.getTextContent());
					if (value.equals("airport"))
						d.setAirport(item.getTextContent());
					if (value.equals("arrivedEng"))
						d.setArrivedEng(item.getTextContent());
					if (value.equals("arrivedKor"))
						d.setArrivedKor(item.getTextContent());
					if (value.equals("boardingEng"))
						d.setBoardingEng(item.getTextContent());
					if (value.equals("boardingKor"))
						d.setBoardingKor(item.getTextContent());
					if (value.equals("city"))
						d.setCity(item.getTextContent());
					if (value.equals("etd")) {
						String time = item.getTextContent().substring(0, 2) + ":"
								+ item.getTextContent().substring(2, item.getTextContent().length());
						d.setEtd(time);
					}
					if (value.equals("gate"))
						d.setGate(item.getTextContent());
					if (value.equals("io"))
						d.setIo(item.getTextContent());
					if (value.equals("line"))
						d.setLine(item.getTextContent());
					if (value.equals("rmkEng"))
						d.setRmkEng(item.getTextContent());
					if (value.equals("rmkKor"))
						d.setRmkKor(item.getTextContent());
					if (value.equals("std")) {
						String time = item.getTextContent().substring(0, 2) + ":"
								+ item.getTextContent().substring(2, item.getTextContent().length());
						d.setStd(time);
					}
				}
				dto.add(d);
			}
			modelAndView.addObject("pageNo", pageNo);
			modelAndView.addObject("totalCount", totalCount);
			modelAndView.addObject("page", dto);
			modelAndView.setViewName("/airportInfoList/todayAirportListBoard");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
