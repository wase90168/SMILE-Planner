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
	
	@Column(nullable = false, length = 45)
	private String name;
	 
	@Column(nullable = false)
	private float budget;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar deadline;
	
	@Column (nullable = true)
	@Temporal(TemporalType.DATE)
	private Calendar realEnd;
	
	@Column(nullable = false, length = 45)
	private String description;
	
	@Column(nullable = false)
	private int status;
	
	@Column(nullable = false, length = 45)
	private String lastChanged;

	public ProjectModel() {
		super();
	}

	@OneToMany (cascade = CascadeType.PERSIST)
	private MilestoneModel milestone;
	
	
	public ProjectModel(String name, float budget, Calendar deadline, Calendar realEnd, String description, int status,
			String lastChanged) {
		super();
		this.name = name;
		this.budget = budget;
		this.deadline = deadline;
		this.realEnd = realEnd;
		this.description = description;
		this.status = status;
		this.lastChanged = lastChanged;
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

	public Calendar getDeadline() {
		return deadline;
	}

	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	public Calendar getRealEnd() {
		return realEnd;
	}

	public void setRealEnd(Calendar realEnd) {
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

	
}
