package project.chatbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import project.domain.entity.CountryCodeEntity;
import project.domain.entity.EmployeesEntity;
import project.domain.repository.CountryCodeEntityRepository;
import project.domain.repository.EmployeesEntityRepository;

@Service
public class KomoranService {

	@Autowired
	private Komoran komoran;

	@Autowired
	private CountryCodeEntityRepository countryCodeRepository;

	@Autowired
	private FindCountryWarning findWarningService;

	public WarningInfoDTO WarningMessege(String message, Model model) {

		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm");
		CountryCodeEntity countrycode = null;

		model.addAttribute("time", today.format(timeFormatter));

		KomoranResult result = komoran.analyze(message);

		// 문자에서 명사들만 추출한 목록(중복제거하기위해 set으로 저장)
		Set<String> nouns = result.getNouns().stream().collect(Collectors.toSet()); // set으로 저장하여 중복제거

		nouns.forEach((noun) -> {
			System.out.println(">>>:" + noun);
		});// 메세지에서 명사추출

		for (String token : nouns) {

			if (token.equals("위험") || token.equals("경보") || token.equals("여행") || token.equals("정보") || token.equals("단계")){
				continue;
			} else { //나라 정보가 있는 경우
				countrycode = countryCodeRepository.findByCountryNameKor(token); //입력한 국가 이름과 일치하는 정보가 있는지 질의
				if (countrycode == null) { // 일치한 국가가 없다면 해당 이름이 들어간 나라 질의
					countrycode = countryCodeRepository.findByCountryNameKorContaining(token);
				}
				model.addAttribute("country", token);
				break;
			}
		}
		if (countrycode == null) { // 입력한 국가 정보가 없는 경우
			model.addAttribute("countrycode", null);
			WarningInfoDTO info = findWarningService.findDataByCountryCode(null);
			return info;
		} else { //입력한 국가 정보가 있는 경우
			model.addAttribute("countrycode", countrycode.getConutryCodeToTwoOfWord());

			WarningInfoDTO info = findWarningService.findDataByCountryCode(countrycode.getConutryCodeToTwoOfWord());
			return info;
		}
		
	}

	public MessageDTO nlpAnalyze(String message) {

		KomoranResult result = komoran.analyze(message);

		// 문자에서 명사들만 추출한 목록(중복제거하기위해 set으로 저장)
		Set<String> nouns = result.getNouns().stream().collect(Collectors.toSet()); // set으로 저장하여 중복제거
		nouns.forEach((noun) -> {
			System.out.println(">>>:" + noun);
		});// 메세지에서 명사추출

		return analyzeToken(nouns);
	}
	// 질의 종류 확인하는 기능
	public String AnalyzeType(String message) {
		KomoranResult result = komoran.analyze(message);

		// 문자에서 명사들만 추출한 목록(중복제거하기위해 set으로 저장)
		Set<String> nouns = result.getNouns().stream().collect(Collectors.toSet()); // set으로 저장하여 중복제거
		for (String token : nouns) {
			if (token.contains("위험") || token.contains("경보")) {
				return "위험 경보 조회";
			}
		}
		return "기타 조회";
	}

	// 입력된 목적어를 하나씩 파악하여 DB에서 검색하기위해 decisionTree()메서드로 전달
	private MessageDTO analyzeToken(Set<String> nouns) {

		LocalDateTime today = LocalDateTime.now();
		// DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("a h:mm");
		MessageDTO messageDTO = MessageDTO.builder().time(today.format(timeFormatter))// 시간세팅
				.build();

		// 1차 의도가 존재하는지 파악
		for (String token : nouns) {
			Optional<ChatBotIntention> result = decisionTree(token, null);
			if (result.isEmpty())
				continue; // 존재하지 않으면 다음 토큰 검색

			System.out.println(">>>>> 1차 토큰 : " + token); // 1차 토큰 확인
			Set<String> next = nouns.stream().collect(Collectors.toSet()); // 1차 목록 복사
			next.remove(token); // 목록에서 1차 토큰 제거
			// 2차분석 메서드
			AnswerDTO answer = analyzeToken(next, result).toAnswerDTO();

			// 전화인 경우 전화,전화번호,번호 탐색
			if (token.contains("전화") || token.contains("번호")) {
				PhoneInfo phone = analyzeTokenIsPhone(next);
				answer.phone(phone);// 전화인 경우에만 전화 데이터

			} else if (token.contains("안녕")) {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
				messageDTO.today(today.format(dateFormatter));// 처음 접속할때만 날짜표기

			} else if (token.contains("근태") || token.contains("출근")) {
				// 근태, 출근

			}

			messageDTO.answer(answer);// 토큰에 대한 응답정보

			return messageDTO;
		}

		// 분석 명사들이 등록한 의도와 일치하는게 존재하지 않을 경우 null
		AnswerDTO answer = decisionTree("기타", null).get().getAnswer().toAnswerDTO();
		messageDTO.answer(answer);// 토큰에 대한 응답정보
		return messageDTO;
	}

	@Autowired
	EmployeesEntityRepository member;

	// 전화문의인 경우 DB에서 사원을 찾아서 처리
	private PhoneInfo analyzeTokenIsPhone(Set<String> next) {
		for (String name : next) {
			Optional<EmployeesEntity> m = member.findByName(name);
			if (m.isEmpty())
				continue;
			// 존재하면 해당부서명, 전화번호를 리턴
			String deptName = m.get().getDepartmentNo().getDepartmentName();
			String phone = m.get().getPhone();
			String memberName = m.get().getName();
			String extension = m.get().getExtension();
			return PhoneInfo.builder().deptName(deptName).phone(phone).memberName(memberName).extension(extension)
					.build();
		}
		return null;

	}

	// 1차 의도가 존재하면
	// 하위 의도가 존재하는지 파악
	private Answer analyzeToken(Set<String> next, Optional<ChatBotIntention> upper) {

		for (String token : next) {
			// 1차 의도를 부모로 하는 토큰이 존재하는지 파악
			Optional<ChatBotIntention> result = decisionTree(token, upper.get());
			if (result.isEmpty())
				continue;
			System.out.println(">>>>> 2차 토큰 : " + token);

			// 1차-2차 의도 존재하는 경우
			return result.get().getAnswer(); // result2.get()해야 엔티티가 나옴
		}
		// 1차만 존재하는 경우 답변
		return upper.get().getAnswer();
	}

	@Autowired
	ChatBotIntentionRepository intention;

	// 의도가 존재하는지 DB에서 파악
	private Optional<ChatBotIntention> decisionTree(String token, ChatBotIntention upper) {
		return intention.findByNameAndUpper(token, upper);
	}
}
