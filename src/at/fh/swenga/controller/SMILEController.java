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
	@RequestMapping(value = {"/newProject"}, method = RequestMethod.POST)
	@Transactional
	public String newProject(Model model,
			@RequestParam String projectName, @RequestParam String budget,
			/*@RequestParam String deadline,@RequestParam String plannedEnd,*/  @RequestParam String description,
			/*@RequestParam String status, @RequestParam String lastChange,*/ @RequestParam String progress) {
		
		/* Fields that are not in html form are added manually */
		String status = "1";
		String deadline = "1";
		String plannedEnd = "2017-06-17";
		String lastChange = "2017-06-17";

		Float newBudget = Float.parseFloat(budget);
		int newStatus = Integer.parseInt(status);
		Float newProgress = Float.parseFloat(progress);
		
		
		ProjectModel pm = new ProjectModel(projectName,newBudget,deadline,plannedEnd,description,newStatus,lastChange,newProgress);
		projectRepository.save(pm);
		
		return "forward:/index";
		
	}
	
	/*
	 * add new Workpackage
	 */
	@RequestMapping(value = {"/newWorkpackage"}, method = RequestMethod.POST)
	@Transactional
	public String newWorkpackage(Model model,
			@RequestParam String workpackageName, @RequestParam String description, @RequestParam String responsible, 
			@RequestParam String costs, @RequestParam String progress, @RequestParam long id) {
		
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
		
		WorkpackageModel wm = new WorkpackageModel(workpackageName, durationHours, newCosts, description, status, newProgress, plannedBegin, actualBegin, plannedEnd, actualEnd, project);
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
		return "project_detail";
	}
	
	// No idea what I#m doing but it seems to work...
	@RequestMapping(value = { "/workpackage_detail" })
	public String forwardWorkpackageDetail() {
		return "workpackage_detail";
	}
	
	// No idea what I#m doing but it seems to work...
	@RequestMapping(value = { "/createNewProject" })
	public String forwardCreateNewProject() {
		return "createNewProject";
	}
	
	@RequestMapping(value = { "/newWorkpackage"}, method = RequestMethod.GET)
	public String forwardCreateNewWorkpackage(Model model, @RequestParam long id) {
		ProjectModel project = projectRepository.findProjectByIdproject(id);
		model.addAttribute("project",project);
		return "createNewWorkpackage";
	}
	
	@RequestMapping("/fillEmployeeList")
	public String fillEmployeeList() {
 
		Calendar now = Calendar.getInstance();
		employeeManager.addEmployee(new EmployeeModel(1, "Max", "Mustermann", now));
		employeeManager.addEmployee(new EmployeeModel(2, "Mario", "Rossi", now));
		employeeManager.addEmployee(new EmployeeModel(3, "John", "Doe", now));
		employeeManager.addEmployee(new EmployeeModel(4, "Jane", "Doe", now));
		employeeManager.addEmployee(new EmployeeModel(5, "Maria", "Noname", now));
		employeeManager.addEmployee(new EmployeeModel(6, "Josef", "Noname", now));
 
		return "forward:/index";
	}
	

	@RequestMapping("/deleteProject")
	public String delete(Model model, @RequestParam long id) {
		projectRepository.removeByIdproject(id);
		return "forward:index";
	}
	

	
	@RequestMapping("/searchEmployees")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("employees", employeeManager.getFilteredEmployees(searchString));
		return "listEmployees";
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
	public String showAddEmployeeForm(Model model) {
		return "editEmployee";
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public String addEmployee(@Valid @ModelAttribute EmployeeModel newEmployeeModel, BindingResult bindingResult,
			Model model) {
 
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listEmployees";
		}
 
		EmployeeModel employee = employeeManager.getEmployeeBySSN(newEmployeeModel.getSsn());
 
		if (employee != null) {
			model.addAttribute("errorMessage", "Employee already exists!<br>");
		} else {
			employeeManager.addEmployee(newEmployeeModel);
			model.addAttribute("message", "New employee " + newEmployeeModel.getSsn() + " added.");
		}
 
		return "forward:/listEmployees";
	}
	
	@RequestMapping(value = "/editEmployee", method = RequestMethod.GET)
	public String showChangeEmployeeForm(Model model, @RequestParam int ssn) {
		EmployeeModel employee = employeeManager.getEmployeeBySSN(ssn);
		if (employee != null) {
			model.addAttribute("employee", employee);
			return "editEmployee";
		} else {
			model.addAttribute("errorMessage", "Couldn't find employee " + ssn);
			return "forward:/listEmployees";
		}
	}
	
	@RequestMapping(value = "/editEmployee", method = RequestMethod.POST)
	public String changeEmployee(@Valid @ModelAttribute EmployeeModel changedEmployeeModel, BindingResult bindingResult,
			Model model) {
 
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listEmployees";
		}
 
		EmployeeModel employee = employeeManager.getEmployeeBySSN(changedEmployeeModel.getSsn());
 
		if (employee == null) {
			model.addAttribute("errorMessage", "Employee does not exist!<br>");
		} else {
			employee.setSsn(changedEmployeeModel.getSsn());
			employee.setFirstName(changedEmployeeModel.getFirstName());
			employee.setLastName(changedEmployeeModel.getLastName());
			employee.setDayOfBirth(changedEmployeeModel.getDayOfBirth());
			model.addAttribute("message", "Changed employee " + changedEmployeeModel.getSsn());
		}
 
		return "forward:/listEmployees";
	}
	/*
	@RequestMapping(value = "/sanitize", method = RequestMethod.GET)
	public String testSanitization(Model model) {
		// Teststrings:
		 // <p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>
		 // <img src='http://placehold.it/350x150' />
		// <script>alert(document.cookie)</script>
		 //
		
		String unsanitized = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
		String sanitized = Jsoup.clean(unsanitized, Whitelist.basic());
		
		// Whitelist Values: http://jsoup.org/apidocs/org/jsoup/safety/Whitelist.html
		
		model.addAttribute("sanitized", sanitized);
		model.addAttribute("unsanitized", unsanitized);
		return "testSanitization";
	}
	 */
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
		return "showError";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login";
	}
	
	

}