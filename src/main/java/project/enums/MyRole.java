package project.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MyRole {
	
	EMPLOYEE("ROLE_EMPLOYEE"),
	PERSONALMANAGER("ROLE_PERSONAL"),
	NONE("ROLE_RESIGNED"), //230120 νμ μΆκ°
	CEO("ROLE_CEO");
	
	private final String role;

}