package project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import project.domain.entity.DepartmentsEntity;
import project.domain.repository.DepartmentsEntityRepository;

@SpringBootApplication
public class PinkBalloonApplication {
	/* 20230110 문대현 수정 */

	// 프로젝트 작업시 EC2 환경에 따른 오류 안나게 하기 위해서 생성
	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}

	public static void main(String[] args) {
		SpringApplication.run(PinkBalloonApplication.class, args);
	}

	// 프로젝트 시작시 부서명 검색해서 없는경우 디폴트 값 추가해 주는 기능
	@Bean
	public CommandLineRunner run(DepartmentsEntityRepository departmentsRepository) throws Exception {
		return (String[] args) -> {

			// 부서 정보 모두 조회
			List<DepartmentsEntity> departments = departmentsRepository.findAll();
			
			// 기존에 부서 정보 없을시에 넣을 디폴트 부서 정보
			List<DepartmentsEntity> defaultDepartments = new ArrayList<>();

			defaultDepartments.add(DepartmentsEntity.builder().departmentName("총무부").departmentHead("미정").build());
			defaultDepartments.add(DepartmentsEntity.builder().departmentName("영업부").departmentHead("미정").build());
			defaultDepartments.add(DepartmentsEntity.builder().departmentName("인사부").departmentHead("미정").build());
			defaultDepartments.add(DepartmentsEntity.builder().departmentName("기획부").departmentHead("미정").build());
			defaultDepartments.add(DepartmentsEntity.builder().departmentName("회계부").departmentHead("미정").build());
			defaultDepartments.add(DepartmentsEntity.builder().departmentName("개발부").departmentHead("미정").build());
			defaultDepartments.add(DepartmentsEntity.builder().departmentName("사업부").departmentHead("미정").build());

			for (DepartmentsEntity defaultEntity : defaultDepartments) {

				Boolean check = true; // 기존 부서가 없어서 등록할 필요가 있는 지 여부

				for (DepartmentsEntity entity : departments) {
					
					if (defaultEntity.getDepartmentName().equals(entity.getDepartmentName())) {
						// 기존에 부서명이 등록되어 있는경우 등록하지 않음
						check = false;

						break;
					}
				}

				// 기존에 없었던 부서명 등록 과정
				if (check == true) {
				departmentsRepository.save(defaultEntity);

				}
			}
		};
	}
}
