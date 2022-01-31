package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;

class IngredientToIngredientCommandTest {

	public static final Recipe RECIPE = new Recipe();
	public static final BigDecimal AMOUNT = new BigDecimal("1");
	public static final String DESCRIPTION = "Description";
	public static final Long ID_VALUE = new Long(1L);
	public static final Long UOM_ID = new Long(2L);
	
	IngredientToIngredientCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		
	}
	
	void testNullObject() throws Exception {
		
		assertNull(converter.convert(null));
	}
	
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Ingredient()));
	}

	@Test
	void testConvert() {
		
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(UOM_ID);
		
		ingredient.setUnitOfMeasure(unitOfMeasure);		
		
		//when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		
		//then
		assertNotNull(ingredientCommand);
		assertNotNull(ingredientCommand.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
		assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
	}
	
	@Test
	void convertWithNullUOM() throws Exception {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		
		//when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		
		//then
		assertNotNull(ingredientCommand);
		assertNull(ingredient.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}

}
