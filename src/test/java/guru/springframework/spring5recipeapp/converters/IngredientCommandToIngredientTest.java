package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;

class IngredientCommandToIngredientTest {

	public static final Recipe RECIPE = new Recipe();
	public static final BigDecimal AMOUNT = new BigDecimal("1");
	public static final String DESCRIPTION = "Description";
	public static final Long ID_VALUE = new Long(1L);
	public static final Long UOM_ID = new Long(2L);
	
	IngredientCommandToIngredient converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	void testConvert() throws Exception{
		
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID_VALUE);
		ingredientCommand.setAmount(AMOUNT);
		ingredientCommand.setDescription(DESCRIPTION);
	    UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
	    unitOfMeasureCommand.setId(UOM_ID);
	    
	    ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);
	    
	    //when
	    Ingredient ingredient = converter.convert(ingredientCommand);
	    
	    //then
	    assertNotNull(ingredient);
	    assertNotNull(ingredient.getUnitOfMeasure());
	    assertEquals(ID_VALUE, ingredient.getId());
	    assertEquals(AMOUNT, ingredient.getAmount());
	    assertEquals(DESCRIPTION, ingredient.getDescription());
	    assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
	}
	
	@Test
	void convertWithNullUOM() throws Exception{
		
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID_VALUE);
		ingredientCommand.setAmount(AMOUNT);
		ingredientCommand.setDescription(DESCRIPTION);
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		
		
		
		//when
		Ingredient ingredient = converter.convert(ingredientCommand);
		
		//then
		assertNotNull(ingredient);
		assertNull(ingredient.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());
	}

}
