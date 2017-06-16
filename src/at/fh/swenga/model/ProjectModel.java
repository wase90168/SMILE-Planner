package at.fh.swenga.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Project")

public class ProjectModel implements java.io.Serializable {

	
	@Id
	@Column(name = "idproject")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproject;
	
	@Column(nullable = true, length = 45)
	private String name;
	 
	@Column(nullable = true)
	private float budget;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private String deadline;
	
	@Column (nullable = true)
	@Temporal(TemporalType.DATE)
	private String realEnd;
	
	@Column(nullable = true, length = 45)
	private String description;
	
	@Column(nullable = true)
	private int status;
	
	@Column(nullable = true)
	private float progress;

	
	@Column(nullable = false, length = 45)
	private String lastChanged;

	public ProjectModel() {
		super();
	}

	@OneToMany (cascade = CascadeType.PERSIST)
	private MilestoneModel milestone;
	
	
	public ProjectModel(String name, float budget, String deadline, String realEnd, String description, int status,
			String lastchange, float progress) {
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

	public int getIdproject() {
		return idproject;
	}

	public void setIdproject(int idproject) {
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
