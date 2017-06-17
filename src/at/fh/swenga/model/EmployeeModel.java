package at.fh.swenga.model;
 
import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
 
public class EmployeeModel implements Comparable<EmployeeModel> {
 
	private int ssn;
 
	private String firstName;
	private String lastName;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Calendar dayOfBirth;
 
	public EmployeeModel() {
	}
 
	public EmployeeModel(int ssn, String firstName, String lastName, Calendar dayOfBirth) {
		super();
		this.ssn = ssn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dayOfBirth = dayOfBirth;
	}
 
	public int getSsn() {
		return ssn;
	}
 
	public void setSsn(int ssn) {
		this.ssn = ssn;
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
 
	public Calendar getDayOfBirth() {
		return dayOfBirth;
	}
 
	public Date getDayOfBirthAsDate() {
		if (dayOfBirth == null)
			return null;
		return dayOfBirth.getTime();
	}
 
	public void setDayOfBirth(Calendar dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}
 
	@Override
	public int compareTo(EmployeeModel o) {
		return ssn - o.getSsn();
	}
 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ssn;
		return result;
	}
 
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeModel other = (EmployeeModel) obj;
		if (ssn != other.ssn)
			return false;
		return true;
	}
 
}