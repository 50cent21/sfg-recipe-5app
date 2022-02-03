package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final CategoryToCategoryCommand categoryConverter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final NotesToNotesCommand notesConverter;	
	
	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
			IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		
		if(source != null) {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setNotes(notesConverter.convert(source.getNotes()));
		
		if(source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
			      .forEach(category -> recipeCommand.getCategories().add(categoryConverter.convert(category)));
		}
		
		if(source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
			      .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		return recipeCommand;
		}
		
		return null;
	}

}
