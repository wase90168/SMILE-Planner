package at.fh.swenga.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Workpackage")
public class WorkpackageModel implements java.io.Serializable{

	@Id
	@Column(name = "idworkpackage")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idworkpackage;
	
	@Column(nullable = false, length = 45)
	private String name;
	
	@Column
	private int dauerStunden;
	
	@Column
	private float cost;
	
	@Column (nullable = true, length = 45)
	private String description;
	
	@Column
	private int status;
	
	@Column (nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar plannedBegin;
	
	@Column (nullable = true)
	@Temporal(TemporalType.DATE)
	private Calendar actualBegin;
	
	@Column (nullable = false)
	@Temporal(TemporalType.DATE)
	private Calendar plannedEnd;
	
	@Column (nullable = true)
	@Temporal(TemporalType.DATE)
	private Calendar actualEnd;
	
	@Column (nullable = true, length = 45)
	private String toDo;
	
	@ManyToOne
	private ProjectModel project;
	
	@ManyToOne
	private MilestoneModel milestone;
	
	@ManyToOne
	private UserModel user;
	
	public WorkpackageModel() {
		
	}

	public WorkpackageModel(String name, int dauerStunden, float cost, String description, int status,
			Calendar plannedBegin, Calendar actualBegin, Calendar plannedEnd, Calendar actualEnd, String toDo,
			ProjectModel project, MilestoneModel milestone, UserModel user) {
		super();
		this.name = name;
		this.dauerStunden = dauerStunden;
		this.cost = cost;
		this.description = description;
		this.status = status;
		this.plannedBegin = plannedBegin;
		this.actualBegin = actualBegin;
		this.plannedEnd = plannedEnd;
		this.actualEnd = actualEnd;
		this.toDo = toDo;
		this.project = project;
		this.milestone = milestone;
		this.user = user;
	}

	public int getIdworkpackage() {
		return idworkpackage;
	}

	public void setIdworkpackage(int idworkpackage) {
		this.idworkpackage = idworkpackage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDauerStunden() {
		return dauerStunden;
	}

	public void setDauerStunden(int dauerStunden) {
		this.dauerStunden = dauerStunden;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
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

	public Calendar getPlannedBegin() {
		return plannedBegin;
	}

	public void setPlannedBegin(Calendar plannedBegin) {
		this.plannedBegin = plannedBegin;
	}

	public Calendar getActualBegin() {
		return actualBegin;
	}

	public void setActualBegin(Calendar actualBegin) {
		this.actualBegin = actualBegin;
	}

	public Calendar getPlannedEnd() {
		return plannedEnd;
	}

	public void setPlannedEnd(Calendar plannedEnd) {
		this.plannedEnd = plannedEnd;
	}

	public Calendar getActualEnd() {
		return actualEnd;
	}

	public void setActualEnd(Calendar actualEnd) {
		this.actualEnd = actualEnd;
	}

	public String getToDo() {
		return toDo;
	}

	public void setToDo(String toDo) {
		this.toDo = toDo;
	}

	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}

	public MilestoneModel getMilestone() {
		return milestone;
	}

	public void setMilestone(MilestoneModel milestone) {
		this.milestone = milestone;
	}
	
	
	
	
}
