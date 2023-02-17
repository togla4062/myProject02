package project.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "images")
@Entity
@Getter
//230104 안나 생성
public class ImagesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private long imageNo;
	
	@Column(name = "old_name", nullable = false)
	private String oldName; //이미지 원래이름
	
	@Column(name = "new_name")
	private String newName; //이미지 새이름
	
	@Column(nullable = false)
	private String url; //이미지 url

	@Column(name = "imgkey")
	private String imgkey;
	/* 230109 한아 수정 : OneToOne으로 변경
	@JoinColumn(name = "employee_no", nullable = false)
	@ManyToOne
	private EmployeesEntity employeeNo; //사원번호
	*/


}

