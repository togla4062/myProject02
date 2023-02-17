package project.domain.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EmployeesDeleteDTO {
	
	private boolean deleteStatus;//삭제여부
	private LocalDate resignDate;

}

