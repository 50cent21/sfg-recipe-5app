package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	
	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		
		if(source != null) {
			final Ingredient ingredient = new Ingredient();
			ingredient.setId(source.getId());
			
			if(source.getRecipeId() != null) {
				Recipe recipe = new Recipe();
				recipe.setId(source.getRecipeId());
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			
			ingredient.setAmount(source.getAmount());
			ingredient.setDescription(source.getDescription());
			ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
			return ingredient;
		}
		
		
		return null;
	}

}
