package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;
import java.util.List;

@Controller
public class UserController {

	@Qualifier("userServiceImpl")
	@Autowired
	UserService userService;

	@GetMapping(value = "/")
	public String printUsersPage(ModelMap model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("usersList", users);
		return "index";
	}

	@GetMapping("/add")
	public String showSignUpForm(User user, ModelMap model) {
		model.addAttribute("user", user);
		return "add-user";
	}

	@PostMapping("/add")
	public String addUser(User user, Model model) {
		userService.addUser(user);
		model.addAttribute("userList", userService.getAllUsers());
		return "redirect:/";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "update-user";
	}

	@PostMapping("/edit/{id}")
	public String updateUser(@PathVariable("id") long id, User user, Model model) {
		user.setId(id);
		userService.updateUser(user);
		model.addAttribute("userList", userService.getAllUsers());
		return "redirect:/";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		userService.removeUserById(id);
		model.addAttribute("userList", userService.getAllUsers());
		return "redirect:/";
	}
}