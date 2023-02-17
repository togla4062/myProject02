package project.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "country_code")
@Entity
@Getter
public class CountryCodeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_no", unique = true, nullable = false)
	private long countryNo; //번호
	
	@Column(name = "country_name_kor")
	private String countryNameKor; //국가이름 국문
	
	@Column(name = "country_code_to_number")
	private long countryCodeToNumber; //국가코드 숫자
	
	@Column(name = "conutry_code_to_two_of_word")
	private String conutryCodeToTwoOfWord; //국가코드 영어2자
	
	@Column(name = "conutry_code_to_three_of_word")
	private String conutryCodeToThreeOfWord; //국가코드 영어3자
	
}
