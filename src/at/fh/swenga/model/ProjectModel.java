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

public class ProjectModel implements Comparable<ProjectModel> {

	@Id
	@Column(name = "idproject")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idproject;
	
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
	
	@Column(nullable = true, length = 250)
	private String description;
	
	@Column(nullable = true)
	private int status;
	
	@Column(nullable = true)
	private float progress;
	
	@Column(nullable = true)
	private int duration;

	@Column(nullable = false, length = 45)
	private String lastChanged;

	@OneToMany(cascade = CascadeType.PERSIST)
	private List<WorkpackageModel> workpackage;
	
	
	public ProjectModel() {
		super();
	}	
	
	public ProjectModel(String name, float budget, String deadline, String realEnd, String description, int status, String lastchange, float progress, int duration) {
		super();
		this.name = name;
		this.budget = budget;
		this.deadline = deadline;
		this.realEnd = realEnd;
		this.description = description;
		this.status = status;
		this.lastChanged = lastchange;
		this.progress = progress;
		this.duration = duration;
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

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(budget);
		result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + duration;
		result = prime * result + (int) (idproject ^ (idproject >>> 32));
		result = prime * result + ((lastChanged == null) ? 0 : lastChanged.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(progress);
		result = prime * result + ((realEnd == null) ? 0 : realEnd.hashCode());
		result = prime * result + status;
		result = prime * result + ((workpackage == null) ? 0 : workpackage.hashCode());
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
		ProjectModel other = (ProjectModel) obj;
		if (Float.floatToIntBits(budget) != Float.floatToIntBits(other.budget))
			return false;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (duration != other.duration)
			return false;
		if (idproject != other.idproject)
			return false;
		if (lastChanged == null) {
			if (other.lastChanged != null)
				return false;
		} else if (!lastChanged.equals(other.lastChanged))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(progress) != Float.floatToIntBits(other.progress))
			return false;
		if (realEnd == null) {
			if (other.realEnd != null)
				return false;
		} else if (!realEnd.equals(other.realEnd))
			return false;
		if (status != other.status)
			return false;
		if (workpackage == null) {
			if (other.workpackage != null)
				return false;
		} else if (!workpackage.equals(other.workpackage))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(ProjectModel o) {
		return idproject - o.getIdproject();
	}

}
