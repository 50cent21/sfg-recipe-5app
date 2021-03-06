 package guru.springframework.spring5recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import guru.springframework.spring5recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final IngredientService ingredientService;
	private final RecipeService recipeService;
	private final UnitOfMeasureService unitOfMeasureService;
	
	public IngredientController(IngredientService ingredientService, RecipeService recipeService,
			UnitOfMeasureService unitOfMeasureService) {
		this.ingredientService = ingredientService;
		this.recipeService = recipeService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model theModel) {
		log.debug("Getting ingredient list for recipe id: " + recipeId);
		
		//use command object to avoid lazy load error in Thymeleaf
		theModel.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId,
			                           @PathVariable String id, Model theModel) {
		
		theModel.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		return "recipe/ingredient/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable String recipeId, Model theModel) {
		
		//make sure we have a good id value
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		//todo raise exception if null
		
		//return back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		theModel.addAttribute("ingredient", ingredientCommand);
		//init uom
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
		
		theModel.addAttribute("uomList", unitOfMeasureService.listAllUnitsOfMeasure());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId,
			                             @PathVariable String id, Model theModel) {
		
		theModel.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
		
		theModel.addAttribute("uomList", unitOfMeasureService.listAllUnitsOfMeasure());
		
		return "recipe/ingredient/ingredientform";
	}
	
	
	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		log.debug("Saved Recipe id: " + savedCommand.getRecipeId());
		log.debug("Saved Ingredient id: " + savedCommand.getId());
		
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteRecipeIngredient(@PathVariable String recipeId,
			                             @PathVariable String id) {
		
		log.debug("Deleting an ingredient with id: " + id);
		
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
		
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	
}
