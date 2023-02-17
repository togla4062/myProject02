package project.chatbot;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatBotIntentionRepository extends JpaRepository<ChatBotIntention, Long>{

	Optional<ChatBotIntention> findByName(String token); //Optional<ChatBotIntention>을 사용하면 파악하기가 더 수월 

	Optional<ChatBotIntention> findByNameAndUpper(String token, ChatBotIntention upper);

}
