package at.fh.swenga.model;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Project")

public class ProjectModel implements java.io.Serializable {

	@Id
	@Column(name = "idproject")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idproject;
	
	@Column(nullable = true, length = 45)
	private String name;
	 
	@Column(nullable = true)
	private float budget;

	@Column(nullable = true)
	//@Temporal(TemporalType.DATE)
	private String deadline;
	
	@Column (nullable = true)
	//@Temporal(TemporalType.DATE)
	private String realEnd;
	
	@Column(nullable = true, length = 45)
	private String description;
	
	@Column(nullable = true)
	private int status;
	
	@Column(nullable = true)
	private float progress;

	
	@Column(nullable = false, length = 45)
	private String lastChanged;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<WorkpackageModel> workpackage;
	
	
	public ProjectModel() {
		super();
	}	
	
	public ProjectModel(String name, float budget, String deadline, String realEnd, String description, int status, String lastchange, float progress) {
		super();
		this.name = name;
		this.budget = budget;
		this.deadline = deadline;
		this.realEnd = realEnd;
		this.description = description;
		this.status = status;
		this.lastChanged = lastchange;
		this.progress = progress;
	}

	public long getIdproject() {
		return idproject;
	}

	public void setIdproject(long idproject) {
		this.idproject = idproject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(String realEnd) {
		this.realEnd = realEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLastChanged() {
		return lastChanged;
	}

	public void setLastChanged(String lastChanged) {
		this.lastChanged = lastChanged;
	}
	
	public float getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
