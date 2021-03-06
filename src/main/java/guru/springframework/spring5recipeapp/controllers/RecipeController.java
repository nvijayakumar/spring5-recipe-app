/**
 * 
 */
package guru.springframework.spring5recipeapp.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vijayakumar
 * @Since  08-Mar-2022
 *
 */
@Slf4j
@Controller
public class RecipeController {
	
	private static final String RECIPE_RECIPEFORM_URL = "/recipe/recipeform";

	RecipeService recipeService;
	
	//Automatic @Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		
		return "/recipe/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return RECIPE_RECIPEFORM_URL;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable Long id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return RECIPE_RECIPEFORM_URL;
	}
	
	@PostMapping("/recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(e -> log.debug(e.toString()));
			
			return RECIPE_RECIPEFORM_URL;
		}
		
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		return "redirect:/recipe/"+savedRecipeCommand.getId()+"/show";
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteById(@PathVariable Long id) {
		recipeService.deleteById(id);
		
		return "redirect:/";
	}
	
	/* All exception handling moved to controller advice.
	 * 
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling not found exception.");
		log.error(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormat(Exception exception) {
		log.error("Handling number format exception in controller.");
		log.error(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("400error");
		modelAndView.addObject("exception", exception);
		return modelAndView;
	}*/
}
