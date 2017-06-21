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

public class WorkpackageModel implements Comparable<WorkpackageModel> {

	@Id
	@Column(name = "idworkpackages")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idworkpackages;
	
	@ManyToOne (cascade = CascadeType.PERSIST)
	private ProjectModel project;
	
	@Column(nullable = true, length = 45)
	private String name;
	
	//Duration is not hours actually, we changed it to days (makes more sense).
	@Column(nullable = true, length = 45)
	private String durationHours;
	
	@Column(nullable = true)
	private float cost;
	
	@Column(nullable = true, length = 250)
	private String description;
	
	@Column(nullable = true)
	private int status;
	
	@Column(nullable = true)
	private float progress;
	
	@Column(nullable = true, length = 45)
	private String plannedBegin;
	
	@Column(nullable = true, length = 45)
	private String actualBegin;
	
	@Column(nullable = true, length = 45)
	private String plannedEnd;
	
	@Column(nullable = true, length = 45)
	private String actualEnd;
	
	@Column(nullable = true, length = 45)
	private String modus;
	
	@Column(nullable = true, length = 45)
	private String responsible;
	
	public WorkpackageModel() {
		super();
	}

	public WorkpackageModel(String name, String durationHours, float cost, String description, int status, int progress,
			String plannedBegin, String actualBegin, String plannedEnd, String actualEnd, ProjectModel project, String modus, String responsible) {
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
		this.modus = modus;
		this.responsible = responsible;
	}

	public int getIdworkpackages() {
		return idworkpackages;
	}

	public void setIdworkpackages(int idworkpackages) {
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

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
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

	public String getModus() {
		return modus;
	}

	public void setModus(String modus) {
		this.modus = modus;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualBegin == null) ? 0 : actualBegin.hashCode());
		result = prime * result + ((actualEnd == null) ? 0 : actualEnd.hashCode());
		result = prime * result + Float.floatToIntBits(cost);
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((durationHours == null) ? 0 : durationHours.hashCode());
		result = prime * result + idworkpackages;
		result = prime * result + ((modus == null) ? 0 : modus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((plannedBegin == null) ? 0 : plannedBegin.hashCode());
		result = prime * result + ((plannedEnd == null) ? 0 : plannedEnd.hashCode());
		result = prime * result + Float.floatToIntBits(progress);
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((responsible == null) ? 0 : responsible.hashCode());
		result = prime * result + status;
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
		WorkpackageModel other = (WorkpackageModel) obj;
		if (actualBegin == null) {
			if (other.actualBegin != null)
				return false;
		} else if (!actualBegin.equals(other.actualBegin))
			return false;
		if (actualEnd == null) {
			if (other.actualEnd != null)
				return false;
		} else if (!actualEnd.equals(other.actualEnd))
			return false;
		if (Float.floatToIntBits(cost) != Float.floatToIntBits(other.cost))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (durationHours == null) {
			if (other.durationHours != null)
				return false;
		} else if (!durationHours.equals(other.durationHours))
			return false;
		if (idworkpackages != other.idworkpackages)
			return false;
		if (modus == null) {
			if (other.modus != null)
				return false;
		} else if (!modus.equals(other.modus))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (plannedBegin == null) {
			if (other.plannedBegin != null)
				return false;
		} else if (!plannedBegin.equals(other.plannedBegin))
			return false;
		if (plannedEnd == null) {
			if (other.plannedEnd != null)
				return false;
		} else if (!plannedEnd.equals(other.plannedEnd))
			return false;
		if (Float.floatToIntBits(progress) != Float.floatToIntBits(other.progress))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (responsible == null) {
			if (other.responsible != null)
				return false;
		} else if (!responsible.equals(other.responsible))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public int compareTo(WorkpackageModel o) {
		return idworkpackages - o.getIdworkpackages();
	}
	
	
}
