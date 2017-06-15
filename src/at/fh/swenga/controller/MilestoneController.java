package at.fh.swenga.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import at.fh.swenga.model.MilestoneManager;
import at.fh.swenga.model.UserModel;

@Controller
public class MilestoneController {

	@Autowired
	private MilestoneManager milestoneManager;
	
	@RequestMapping("/fillUserList")
	public String fillEmployeeList() {
 
		milestoneManager.addUser(new UserModel("David","Mischak","davidm","pwd",true,"empty"));
		milestoneManager.addUser(new UserModel("Markus","Skergeth","mr-get","pwd",true,"empty"));
		milestoneManager.addUser(new UserModel("Sebastian","Wanke","sebastianw","pwd",true,"empty"));
 
		return "forward:/listUsers";
	}

	
}
