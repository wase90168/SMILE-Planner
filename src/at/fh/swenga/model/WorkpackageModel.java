package at.fh.swenga.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Workpackage")

public class WorkpackageModel implements java.io.Serializable {

	@Id
	@Column(name = "idworkpackages")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idworkpackages;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private ProjectModel project;
	
	@Column(nullable = true, length = 45)
	private String name;
	
	@Column(nullable = true, length = 45)
	private String durationHours;
	
	@Column(nullable = true)
	private float cost;
	
	@Column(nullable = true, length = 45)
	private String description;
	
	@Column(nullable = true)
	private int status;
	
	@Column(nullable = true)
	private int progress;
	
	@Column(nullable = true, length = 45)
	private String plannedBegin;
	
	@Column(nullable = true, length = 45)
	private String actualBegin;
	
	@Column(nullable = true, length = 45)
	private String plannedEnd;
	
	@Column(nullable = true, length = 45)
	private String actualEnd;

	public WorkpackageModel() {
		super();
	}

	public WorkpackageModel(String name, String durationHours, float cost, String description, int status, int progress,
			String plannedBegin, String actualBegin, String plannedEnd, String actualEnd, ProjectModel project) {
		super();
		this.name = name;
		this.durationHours = durationHours;
		this.cost = cost;
		this.description = description;
		this.status = status;
		this.progress = progress;
		this.plannedBegin = plannedBegin;
		this.actualBegin = actualBegin;
		this.plannedEnd = plannedEnd;
		this.actualEnd = actualEnd;
		this.project = project;
	}

	public long getIdworkpackages() {
		return idworkpackages;
	}

	public void setIdworkpackages(long idworkpackages) {
		this.idworkpackages = idworkpackages;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDurationHours() {
		return durationHours;
	}

	public void setDurationHours(String durationHours) {
		this.durationHours = durationHours;
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

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getPlannedBegin() {
		return plannedBegin;
	}

	public void setPlannedBegin(String plannedBegin) {
		this.plannedBegin = plannedBegin;
	}

	public String getActualBegin() {
		return actualBegin;
	}

	public void setActualBegin(String actualBegin) {
		this.actualBegin = actualBegin;
	}

	public String getPlannedEnd() {
		return plannedEnd;
	}

	public void setPlannedEnd(String plannedEnd) {
		this.plannedEnd = plannedEnd;
	}

	public String getActualEnd() {
		return actualEnd;
	}

	public void setActualEnd(String actualEnd) {
		this.actualEnd = actualEnd;
	}

	public ProjectModel getProject() {
		return project;
	}

	public void setProject(ProjectModel project) {
		this.project = project;
	}
	
	
	
}
