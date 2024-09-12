package com.example.humanresource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@Autowired
	private UserDataRepository userDataRepository;
	
	@GetMapping("/welcome")
	public String welcome(Model model) {
		model.addAttribute("name", "John Doe");
		model.addAttribute("message", "Welcome to the Spring Boot JSP Example!");
		return "welcome"; // This is the name of the JSP page (welcome.jsp)
	}
	
	@GetMapping("/welcome_with_thy")
    public String welcomeWithThymeleaf(Model model) {
        model.addAttribute("name", "John Doe");
        model.addAttribute("message", "Welcome to the Thymeleaf Example!");
        return "welcome";  // This resolves to /templates/welcome.html
    }
	
	
	@GetMapping("/welcome_with_thy/{user_name}")
	public String welcomeWithThymeleaf(Model model, @PathVariable("user_name") String userName) {
		model.addAttribute("name", userName);
        model.addAttribute("message", "Welcome to the Thymeleaf Example!");
        return "welcome";  // This resolves to /templates/welcome.html
	}
	
	@GetMapping("/welcome_with_param")
	public String welcomeWithThymeleafWithParam(Model model, @RequestParam("name") String userName) {
		model.addAttribute("name", userName);
        model.addAttribute("message", "Welcome to the Thymeleaf Example!");
        return "welcome";  // This resolves to /templates/welcome.html
	}
	
	@GetMapping("/users")
	@ResponseBody
	public List<UserData> getAllUsers() {
        return userDataRepository.findAll();
	}
}