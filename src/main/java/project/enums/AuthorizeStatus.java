package project.enums;

import lombok.Getter;

@Getter
public enum AuthorizeStatus {
	

	UnderApproval("결재대기"),
	FirstApproval("결재중"),
	Approval("승인"),
	Return("반려");
	
	private final String label;
	
	AuthorizeStatus(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }


}

