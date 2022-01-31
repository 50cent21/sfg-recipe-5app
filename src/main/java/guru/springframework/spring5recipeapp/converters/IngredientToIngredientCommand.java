package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;
		
	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {

		if(source != null) {
			IngredientCommand ingredientCommand = new IngredientCommand();
			ingredientCommand.setId(source.getId());
			ingredientCommand.setDescription(source.getDescription());
			ingredientCommand.setAmount(source.getAmount());
			ingredientCommand.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
			return ingredientCommand;
		}
		
		return null;
	}

}
