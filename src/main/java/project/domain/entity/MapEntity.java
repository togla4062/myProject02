package project.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class MapEntity {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column
	private long no;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String latitude;
	
	@Column(nullable = false)
	private String longitude;
	
	
}
