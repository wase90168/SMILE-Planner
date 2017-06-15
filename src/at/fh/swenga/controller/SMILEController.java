package at.fh.swenga.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import at.fh.swenga.model.EmployeeManager;
import at.fh.swenga.model.EmployeeModel;

@Controller
public class SMILEController {
	@Autowired
	private EmployeeManager employeeManager;
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	  if (!registry.hasMappingForPattern("/resources/**")) {
	     registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
	  }
	}
	
	@RequestMapping(value = { "/", "index" })
	public String showAllEmployees(Model model) {
		model.addAttribute("employees", employeeManager.getAllEmployees());
		return "index";
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
	
	@RequestMapping("/deleteEmployee")
	public String delete(Model model, @RequestParam int ssn) {
		boolean isRemoved = employeeManager.remove(ssn);
 
		if (isRemoved) {
			model.addAttribute("warningMessage", "Employee " + ssn + " deleted");
		} else {
			model.addAttribute("errorMessage", "There is no Employee " + ssn);
		}
 
		// Multiple ways to "forward" to another Method
		// return "forward:/listEmployees";
		return showAllEmployees(model);
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
	
	@RequestMapping(value = "/sanitize", method = RequestMethod.GET)
	public String testSanitization(Model model) {
		/* Teststrings:
		 * <p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>
		 * <img src='http://placehold.it/350x150' />
		 * <script>alert(document.cookie)</script>
		 */
		
		String unsanitized = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
		String sanitized = Jsoup.clean(unsanitized, Whitelist.basic());
		
		// Whitelist Values: http://jsoup.org/apidocs/org/jsoup/safety/Whitelist.html
		
		model.addAttribute("sanitized", sanitized);
		model.addAttribute("unsanitized", unsanitized);
		return "testSanitization";
	}

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