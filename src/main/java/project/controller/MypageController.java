
package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.security.MyUserDetails;
import project.service.EmployeesService;

@Controller
public class MypageController{

	@Autowired
	EmployeesService employeesService;
	
	//마이페이지 
	@GetMapping("/employeeMgmt/mypage")
    public String mypage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
		employeesService.findemployee(myUserDetails.getNo(), model); 
        return "employeeMgmt/mypage";
    }
}
