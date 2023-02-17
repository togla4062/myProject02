package project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.domain.entity.CountryCodeEntity;

@Repository
public interface CountryCodeEntityRepository extends JpaRepository<CountryCodeEntity, Long> {

	CountryCodeEntity  findByCountryNameKorContaining(String token);

	CountryCodeEntity findByCountryNameKor(String token);

}
