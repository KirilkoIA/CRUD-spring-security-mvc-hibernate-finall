package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		model.addAttribute("rolesSet", userService.getAllRoles());
		model.addAttribute("user", user);
		return "add-user";
	}

	@PostMapping("/admin/add")
	public String addUser(@ModelAttribute("user")User user, String[] myRoles) {
		Set<Role> setRoles = new HashSet<>();

		for (String roleName : myRoles) {
			setRoles.add(userService.getRoleByName(roleName));
		}

		user.setRoles(setRoles);
		userService.addUser(user);
		return "redirect:/admin";
	}

	@GetMapping("/admin/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("rolesSet", userService.getAllRoles());
		model.addAttribute("user", user);
		return "update-user";
	}

	@PostMapping("/admin/edit/{id}")
	public String updateUser(@ModelAttribute("user")@PathVariable("id") long id, User user, String[] myRoles) {
		user.setId(id);
		Set<Role> setRoles = new HashSet<>();

		for (String roleName : myRoles) {
			setRoles.add(userService.getRoleByName(roleName));
		}

		user.setRoles(setRoles);

		userService.updateUser(user);
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