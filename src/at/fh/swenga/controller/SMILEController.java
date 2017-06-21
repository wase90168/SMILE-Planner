package at.fh.swenga.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import at.fh.swenga.dao.ProjectRepository;
import at.fh.swenga.dao.WorkpackageRepository;
import at.fh.swenga.model.EmployeeManager;
import at.fh.swenga.model.ProjectModel;
import at.fh.swenga.model.WorkpackageModel;

@Controller
public class SMILEController {

	@Autowired
	private EmployeeManager employeeManager;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private WorkpackageRepository workpackageRepository;

	@RequestMapping(value = { "/newProject" }, method = RequestMethod.POST)
	@Transactional
	public String newProject(Model model, @Valid @ModelAttribute ProjectModel project) {

		ProjectModel existingProject = projectRepository.findProjectByIdproject(project.getIdproject());
		
		if (existingProject != null) {
			model.addAttribute("errorMessage", "Project already exists!<br/>");
		} else {
		project.setStatus(1);
		project.setDeadline("<not set>");
		project.setRealEnd("<not set>");
		project.setLastChanged("no changes");

		projectRepository.save(project);
		}
		return "forward:/index";

	}

	/*
	 * add new Workpackage
	 */
	@RequestMapping(value = { "/newWorkpackage" }, method = RequestMethod.POST)
	@Transactional
	public String newWorkpackage(Model model, @Valid @ModelAttribute WorkpackageModel workpackage, @RequestParam int id) {

		ProjectModel projectId = projectRepository.findProjectByIdproject(id);
		
		int status = 1;
		DateFormat beginDate = new SimpleDateFormat("yyyy-MM-dd");
		String actualBegin = String.valueOf(beginDate.toString());
		
		workpackage.setStatus(status);
		workpackage.setActualBegin(actualBegin);
		workpackage.setModus("Not started");
		workpackage.setProject(projectId);

		workpackageRepository.save(workpackage);


		return "forward:/projectDetails";

	}

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (!registry.hasMappingForPattern("/resources/**")) {
			registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
		}
	}

	@RequestMapping(value = { "/", "index" })
	public String showAllProjects(Model model) {
		List<ProjectModel> projects = projectRepository.findAll();
		model.addAttribute("projects", projects);
		return "index";
	}

	// it works :)
	@RequestMapping(value = { "/projectDetails" })
	public String forwardProjectDetailsPage(Model model, @RequestParam int id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		List<WorkpackageModel> workpackages = workpackageRepository.findWorkpackagesByProject(project);

		model.addAttribute("workpackages", workpackages);
		model.addAttribute("project", project);

		// Total amount of project
		float amount = 0;
		for (int x = 0; x < workpackages.size(); x = x + 1) {
			WorkpackageModel wp = workpackages.get(x);
			amount += wp.getCost();
		}
		
		// project duration calculation
		//TODO
		model.addAttribute("totalCost", amount);
		return "project_detail";
	}

	// delete Workpackage
	@RequestMapping(value = { "/deleteWorkpackage" })
	public String deleteWorkpackage(Model model, @RequestParam int id) {
		workpackageRepository.removeByIdworkpackages(id);
		return "forward:/projectDetails?id=" + id;
	}

	// No idea what I#m doing but it seems to work...
	@RequestMapping(value = { "/workpackage_detail" })
	public String forwardWorkpackageDetail(Model model, @RequestParam int id, @RequestParam int id2) {
		WorkpackageModel wp = workpackageRepository.findWorkpackageByIdworkpackages(id);
		ProjectModel project = projectRepository.findProjectByIdproject(id2);
		model.addAttribute("project", project);
		model.addAttribute("wp", wp);
		return "workpackage_detail";
	}

	// No idea what I#m doing but it seems to work...
	@RequestMapping(value = { "/createNewProject" })
	public String forwardCreateNewProject() {
		return "createNewProject";
	}

	@RequestMapping(value = { "/editProject" })
	public String editProject(Model model, @RequestParam int id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		model.addAttribute("project", project);
		return "createNewProject";
	}

	@RequestMapping(value = { "/editWorkpackage" }, method = RequestMethod.GET)
	public String editWorkpackage(Model model, @RequestParam int id, @RequestParam int id2) {
		WorkpackageModel wp = workpackageRepository.findWorkpackageByIdworkpackages(id);
		ProjectModel project = projectRepository.findProjectByIdproject(id2);
		model.addAttribute("project",project);
		model.addAttribute("wp", wp);
		return "createNewWorkpackage";
	}

	@RequestMapping(value = { "/editWorkpackage" }, method = RequestMethod.POST)
	@Transactional
	public String editWorkpackage(Model model, @Valid @ModelAttribute WorkpackageModel changedWorkpackage, @RequestParam int id2) {

		WorkpackageModel wp = workpackageRepository.findWorkpackageByIdworkpackages(changedWorkpackage.getIdworkpackages());
		int projectId = wp.getProject().getIdproject();
		
		wp.setName(changedWorkpackage.getName());
		wp.setDescription(changedWorkpackage.getDescription());
		wp.setCost(changedWorkpackage.getCost());
		wp.setProgress(changedWorkpackage.getProgress());
		workpackageRepository.save(wp);
		
		return "forward:/projectDetails?id=" + projectId;
	}

	// Method to update the project details
	@RequestMapping(value = { "/updateProject" }, method = RequestMethod.POST)
	@Transactional
	public String updateProject(Model model, @Valid @ModelAttribute ProjectModel changedProject, @RequestParam int id) {

		ProjectModel project = projectRepository.findProjectByIdproject(id);

		project.setName(changedProject.getName());
		project.setDescription(changedProject.getDescription());
		project.setBudget(Float.valueOf(changedProject.getBudget()));
		project.setProgress(Float.valueOf(changedProject.getProgress()));
		//Last Change
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		project.setLastChanged(dateFormat.format(date).toString());
		
		return "forward:/projectDetails";
	}

	@RequestMapping(value = { "/newWorkpackage" }, method = RequestMethod.GET)
	public String forwardCreateNewWorkpackage(Model model, @RequestParam int id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		model.addAttribute("project", project);
		return "createNewWorkpackage";
	}

	@RequestMapping(value = { "/updateWorkpackage" }, method = RequestMethod.POST)
	public String updateWorkpackage(Model model, @RequestParam String progress, @RequestParam String duration,
			@RequestParam String current_costs, @RequestParam String mode, @RequestParam String id,
			@RequestParam String id2) {
		int newId = Integer.valueOf(id);
		float newProgress = Float.parseFloat(progress);
		float newCosts = Float.parseFloat(current_costs);
		WorkpackageModel wp = workpackageRepository.findWorkpackageByIdworkpackages(newId);
		wp.setProgress(newProgress);
		wp.setDurationHours(duration);
		wp.setCost(newCosts);
		int newId2 = Integer.valueOf(id2);
		model.addAttribute("project", projectRepository.findProjectByIdproject(newId2));

		String modus = null;

		switch (mode) {
		case "Not started":
			modus = "Not started";
			break;
		case "In progress":
			modus = "In progress";
			break;
		case "Done":
			modus = "Done";
			break;
		case "Delayed":
			modus = "Delayed";
			break;
		case "Waiting":
			modus = "Waiting";
			break;
		default:
			modus = "Not started";
		}

		wp.setModus(modus);

		workpackageRepository.save(wp);

		String forwardString = "forward:/projectDetails?id=" + id2;
		return forwardString;
	}

	@RequestMapping("/deleteProject")
	public String delete(Model model, @RequestParam int id) {
		projectRepository.removeByIdproject(id);
		return "forward:index";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "showError";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}
	//TODO: registration
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userLogin(Model model) {
		return "index";
	}

}