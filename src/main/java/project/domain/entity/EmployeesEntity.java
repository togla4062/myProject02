package project.domain.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.domain.DTO.EmployeesDeleteDTO;
import project.domain.DTO.EmployeesUpdateDTO;
import project.enums.DepartmentRank;
import project.enums.MyRole;

@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
@Entity
@Getter
@Setter
//230104 안나 생성
//230109 한아 수정 : phone, extension 데이터 타입 변경 long -> String
//230109 한아 수정 : joinDate, resignDate, birthDate 데이터 타입 변경 LocalDateTime -> LocalDate
//230116 한아 수정 : positionRank 칼럼 추가
public class EmployeesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long no;//사원번호
	
	@Column(nullable = false)
	private String name;//이름
	
	@Column(nullable = false)
	private String email;//이메일
	
	@Column(nullable = false)
	private String password;//비밀번호
	
	@Column(nullable = true)
	private String phone;//연락처
	
	@Column(name = "join_date")
	private LocalDate joinDate;//입사일
	
	@Column(name = "resign_date")
	private LocalDate resignDate;//퇴사일
	
	private String extension;//내선번호
	
	private long salary;//급여
	
	@Column(name = "birth_date")
	private LocalDate birthDate;//생년월일
	
	@Column(name = "main_work")
	private String mainWork;//주 업무
	
	@Column(name = "delete_status", columnDefinition = "boolean default false")
	private boolean deleteStatus;//삭제여부
	
	@JoinColumn(name = "department_no")
	@ManyToOne
	private DepartmentsEntity departmentNo; //부서번호
	
	@OneToMany(mappedBy = "employeeNo")
	private List<DayOffEntity> dayOff = new ArrayList<>();

	//---------------230109 한아 수정---------------
	
	//이미지
	@JoinColumn(name = "image_no", nullable = true)
	@OneToOne
	private ImagesEntity imageNo; //이미지번호
	
	
	@Enumerated(EnumType.STRING)
	private DepartmentRank position;//직급
	
	@Column(name = "position_rank")
	private long positionRank;//직급순위
	
	//----------------230128수민 추가 
	
	@Column( name ="emp_grade" ,nullable = true)
	private String empGrade; //인사평가 등급 
	
	public EmployeesEntity addEmpgrade(String grade) {
		this.empGrade = grade;
		return null;
	}
	
	
	//직급 position Enum
	@Builder.Default
	@CollectionTable(name = "employees_position")
	@Enumerated(EnumType.STRING) //설정하지 않으면 숫자(ORDINAL)
	@ElementCollection(fetch = FetchType.EAGER) 
	private Set<DepartmentRank> positions = new HashSet<>();
	public EmployeesEntity addposition(DepartmentRank position) { 
		positions.add(position);
		return this;
	}
	
	
	//수정권한 role Enum
	@Builder.Default
	@CollectionTable(name = "my_role")
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)

	private Set<MyRole> roles = new HashSet<>();

	//role 적용
	public EmployeesEntity addRole(MyRole role) {
		roles.clear();
		roles.add(role);
		return this;
	}
	
	@Enumerated(EnumType.STRING)
	private MyRole editAuthority;//수정권한
	
	//수정 권한 부여
	public EmployeesEntity updateManager(EmployeesUpdateDTO dto) {
		this.editAuthority = MyRole.PERSONALMANAGER;
		return null;
	}
	//수정 권한 제거
	public EmployeesEntity updateEmployee(EmployeesUpdateDTO dto) {
		this.editAuthority = MyRole.EMPLOYEE;
		return null;
	}
	//퇴직 처리
	public EmployeesEntity updateDeleteStatus(EmployeesDeleteDTO dto) {
		this.deleteStatus = true;
		this.resignDate = LocalDate.now();
		this.editAuthority = MyRole.NONE;
		return null;
	}
	//퇴직 처리 취소
	public EmployeesEntity updateRollbackStatus(EmployeesUpdateDTO dto) {
		this.deleteStatus = false;
		this.resignDate = null;
		System.out.println(">>>>>>>"+this.position);
		if(this.position == DepartmentRank.DepartmentManager) this.editAuthority = MyRole.PERSONALMANAGER;
		else if(this.position == DepartmentRank.CEO) this.editAuthority = MyRole.CEO;
		else this.editAuthority = MyRole.EMPLOYEE;
		return null;
	}
	//사원 정보 수정
	public EmployeesEntity updateInfo(EmployeesUpdateDTO dto) {
		this.departmentNo=DepartmentsEntity.builder().departmentNo(dto.getDepartmentNo()).build();
		this.name = dto.getName();
		this.email = dto.getEmail();
		this.position = dto.getPosition();
		this.positionRank = dto.getPosition().ordinal();
		this.phone = dto.getPhone();
		this.extension = dto.getExtension();
		this.mainWork = dto.getMainWork();
		this.joinDate = LocalDate.parse(dto.getJoinDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.birthDate = LocalDate.parse(dto.getBirthDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.salary = dto.getSalary();
		if(resignDate==null) return null; //퇴사일 입력안하면 method 종료
		this.resignDate = LocalDate.parse(dto.getResignDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return null;
	}
	public EmployeesEntity changeHeadPosition() {
		this.position = DepartmentRank.DepartmentManager;
		this.positionRank = 1;
		return null;
	}

}
