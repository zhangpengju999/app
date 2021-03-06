package app.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import app.entity.User;
import app.service.PageQuery;
import app.service.UserService;

@Controller
public class UserController {

	@Inject
	private UserService userService;

	@GetMapping("/users")
	public String list(PageQuery query, Model model) {
		Page<User> users = userService.findAll(query);
		model.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/users/new")
	public String create(@ModelAttribute("user") User user, Model model) {
		return "user/edit";
	}

	@PostMapping("/users/new")
	public String create(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			userService.create(user, bindingResult);
			if (!bindingResult.hasErrors())
				return "redirect:/users";
		}
		return create(user, model);
	}

	@GetMapping("/users/{id}/edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "user/edit";
	}

	@PostMapping("/users/{id}/edit")
	public String edit(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			userService.update(id, user, bindingResult);
			if (!bindingResult.hasErrors())
				return "redirect:/users";
		}
		return "user/edit";
	}

	@DeleteMapping("/users/{id}")
	public String delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return "redirect:/users";
	}

}
