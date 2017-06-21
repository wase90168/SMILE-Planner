package at.fh.swenga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import at.fh.swenga.model.ProjectModel;
import at.fh.swenga.model.WorkpackageModel;


@Repository
@Transactional
public interface WorkpackageRepository extends JpaRepository<WorkpackageModel, Integer>{

	public List<WorkpackageModel> findWorkpackagesByProject(ProjectModel project);
	
	public WorkpackageModel findWorkpackageByIdworkpackages(int id);
	
	public List<WorkpackageModel> removeByIdworkpackages(int id);
	
}
