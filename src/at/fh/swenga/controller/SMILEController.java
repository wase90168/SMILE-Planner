package at.fh.swenga.controller;

import java.util.Calendar;
import java.util.List;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import at.fh.swenga.dao.ProjectRepository;
import at.fh.swenga.dao.WorkpackageRepository;
import at.fh.swenga.model.EmployeeManager;
import at.fh.swenga.model.EmployeeModel;
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

	/*
	 * add new Project
	 */
	@RequestMapping(value = { "/newProject" }, method = RequestMethod.POST)
	@Transactional
	public String newProject(Model model, @RequestParam String projectName, @RequestParam String budget,
			/* @RequestParam String deadline,@RequestParam String plannedEnd, */ @RequestParam String description,
			/* @RequestParam String status, @RequestParam String lastChange, */ @RequestParam String progress) {

		/* Fields that are not in html form are added manually */
		String status = "1";
		String deadline = "1";
		String plannedEnd = "2017-06-17";
		String lastChange = "2017-06-17";

		Float newBudget = Float.parseFloat(budget);
		int newStatus = Integer.parseInt(status);
		Float newProgress = Float.parseFloat(progress);

		ProjectModel pm = new ProjectModel(projectName, newBudget, deadline, plannedEnd, description, newStatus,
				lastChange, newProgress);
		projectRepository.save(pm);

		return "forward:/index";

	}
	
	

	/*
	 * add new Workpackage
	 */
	@RequestMapping(value = { "/newWorkpackage" }, method = RequestMethod.POST)
	@Transactional
	public String newWorkpackage(Model model, @RequestParam String workpackageName, @RequestParam String description,
			@RequestParam String responsible, @RequestParam String costs, @RequestParam String progress,
			@RequestParam long id) {

		/* Fields that are not in html form are added manually */
		String durationHours = "24";
		int status = 1;
		String plannedBegin = "2017-06-17";
		String actualBegin = "2017-06-17";
		String plannedEnd = "2017-10-17";
		String actualEnd = "2017-10-17";

		ProjectModel project = projectRepository.findProjectByIdproject(id);

		int newProgress = Integer.parseInt(progress);
		Float newCosts = Float.parseFloat(costs);
		String modus = "Not started";

		WorkpackageModel wm = new WorkpackageModel(workpackageName, durationHours, newCosts, description, status,
				newProgress, plannedBegin, actualBegin, plannedEnd, actualEnd, project, modus);
		workpackageRepository.save(wm);

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
	public String forwardProjectDetailsPage(Model model, @RequestParam long id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		List<WorkpackageModel> workpackages = workpackageRepository.findWorkpackagesByProject(project);

		model.addAttribute("workpackages", workpackages);
		model.addAttribute("project", project);
		
		//Total amount of project
		float amount = 0;
		for (int x = 0; x < workpackages.size(); x = x + 1) {
			WorkpackageModel wp = workpackages.get(x);
			amount += wp.getCost();
		}
		model.addAttribute("totalCost", amount);
		return "project_detail";
	}
	
	//delete Workpackage
	@RequestMapping(value = { "/deleteWorkpackage"})
	public String deleteWorkpackage(Model model, @RequestParam long id) {
		workpackageRepository.removeByIdworkpackages(id);
		return "forward:/projectDetails?id=" + id;
	}

	// No idea what I#m doing but it seems to work...
	@RequestMapping(value = { "/workpackage_detail" })
	public String forwardWorkpackageDetail(Model model, @RequestParam long id, @RequestParam long id2) {
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
	public String editProject(Model model, @RequestParam long id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		model.addAttribute("project", project);
		return "createNewProject";
	}

	// Method to update the project details
	@RequestMapping(value = { "/updateProject" }, method = RequestMethod.POST)
	@Transactional
	public String updateProject(Model model, @RequestParam String projectName, @RequestParam String budget,
			/* @RequestParam String deadline,@RequestParam String plannedEnd, */ @RequestParam String description,
			/* @RequestParam String status, @RequestParam String lastChange, */ @RequestParam String progress, @RequestParam long id) {

		ProjectModel project = projectRepository.findProjectByIdproject(id);
		
		project.setName(projectName);
		project.setDescription(description);
		project.setBudget(Float.valueOf(budget));
		project.setProgress(Float.valueOf(progress));

		return "forward:/projectDetails";
	}

	@RequestMapping(value = { "/newWorkpackage" }, method = RequestMethod.GET)
	public String forwardCreateNewWorkpackage(Model model, @RequestParam long id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		model.addAttribute("project", project);
		return "createNewWorkpackage";
	}
	
	@RequestMapping(value = { "/updateWorkpackage" }, method = RequestMethod.POST)
	public String updateWorkpackage(Model model, @RequestParam String progress, @RequestParam String duration, @RequestParam String current_costs, @RequestParam String mode,@RequestParam String id, @RequestParam String id2) {
		long newId = Long.valueOf(id);
		float newProgress = Float.parseFloat(progress);
		float newCosts = Float.parseFloat(current_costs);
		WorkpackageModel wp = workpackageRepository.findWorkpackageByIdworkpackages(newId);
		wp.setProgress(newProgress);
		wp.setDurationHours(duration);
		wp.setCost(newCosts);
		long newId2 = Long.valueOf(id2);
		model.addAttribute("project",projectRepository.findProjectByIdproject(newId2));
		
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
	public String delete(Model model, @RequestParam long id) {
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

}