package at.fh.swenga.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Milestone")
public class MilestoneModel {

	@Id
	@Column(name = "idmilestone")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idmilestone;
	
	@Column(nullable = false, length = 45)
	private String name;

	@Column()
	private Boolean erreicht;

	@ManyToOne
	private ProjectModel project;
	
	@ManyToOne
	private WorkpackageModel workpackage;
	
	public MilestoneModel() {
		
	}
	
	public MilestoneModel(String name, Boolean erreicht) {
		super();
		this.name = name;
		this.erreicht = erreicht;
	}

	public int getIdmilestone() {
		return idmilestone;
	}

	public void setIdmilestone(int idmilestone) {
		this.idmilestone = idmilestone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getErreicht() {
		return erreicht;
	}

	public void setErreicht(Boolean erreicht) {
		this.erreicht = erreicht;
	}
	
}
