package at.fh.swenga.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import at.fh.swenga.model.UserModel;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "session")
public class MilestoneManager {

	//List of Users
	private List<UserModel> users = new ArrayList<UserModel>();
	
	public void addUser(UserModel user){
		users.add(user);
	}
	
	
	
	
}
