package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

import java.util.List;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/")
	public String printHomePage(ModelMap model) {
		return "home";
	}

	@GetMapping(value = "/admin")
	public String printUsersPage(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("usersList", users);
		return "index";
	}

	@GetMapping("/admin/add")
	public String showSignUpForm(User user, ModelMap model) {
		model.addAttribute("user", user);
		return "add-user";
	}

	@PostMapping("/admin/add")
	public String addUser(User user, Model model) {
		userService.addUser(user);
		model.addAttribute("userList", userService.getAllUsers());
		return "redirect:/admin";
	}

	@GetMapping("/admin/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "update-user";
	}

	@PostMapping("/admin/edit/{id}")
	public String updateUser(@PathVariable("id") long id, User user, Model model) {
		user.setId(id);
		userService.updateUser(user);
		model.addAttribute("userList", userService.getAllUsers());
		return "redirect:/admin";
	}

	@GetMapping("/admin/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		userService.removeUserById(id);
		model.addAttribute("userList", userService.getAllUsers());
		return "redirect:/admin";
	}

	@GetMapping(value = "/user")
	public String printUserPage(Model model) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		return "userpage";
	}
}