package at.fh.swenga.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class UserModel implements java.io.Serializable {

	@Id
	@Column(name = "iduser")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int iduser;
	
	@Column(nullable = false, length = 45)
	private String firstName;
	 
	@Column(nullable = false, length = 45)
	private String lastName;
	
	@Column(nullable = false, length = 45)
	private String username;
	
	@Column(nullable = false, length = 45)
	private String password;
	
	@Column
	private Boolean active;
	
	@Column(nullable = false, length = 45)
	private String toDo;
	 
	//TODO: OneToMany Relationships
	
	public UserModel() {
		
	}

	public UserModel(String firstName, String lastName, String username, String password, Boolean active, String toDo) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.active = active;
		this.toDo = toDo;
	}

	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
	}
	
}
