package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Difficulty;
import guru.springframework.spring5recipeapp.domain.Recipe;

class RecipeCommandToRecipeTest {

	public static final Long RECIPE_ID = new Long(1L);
	public static final Integer COOK_TIME = Integer.valueOf("5");
	public static final Integer PREP_TIME = Integer.valueOf("7");
	public static final String DESCRIPTION = "My Recipe";
	public static final String DIRECTIONS = "Directions";
	public static final Difficulty DIFFICULTY = Difficulty.EASY;
	public static final Integer SERVINGS = Integer.valueOf("3");
	public static final String SOURCE = "Source";
	public static final String URL = "Some URL";
	public static final Long CAT_ID_1 = 1L;
	public static final Long CAT_ID_2 = 2L;
	public static final Long INGRED_ID_1 = 3L;
	public static final Long INGRED_ID_2 = 4L;
	public static final Long NOTES_ID = 9L;
	
	RecipeCommandToRecipe converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(), 
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				new NotesCommandToNotes());
	}
	
	@Test
	void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new RecipeCommand()));
	}

	@Test
	void testConvert() {
		
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(RECIPE_ID);
		recipeCommand.setCookTime(COOK_TIME);
		recipeCommand.setPrepTime(PREP_TIME);
		recipeCommand.setDescription(DESCRIPTION);
		recipeCommand.setDifficulty(DIFFICULTY);
		recipeCommand.setDirections(DIRECTIONS);
		recipeCommand.setServings(SERVINGS);
		recipeCommand.setSource(SOURCE);
		recipeCommand.setUrl(URL);
		
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(NOTES_ID);
		
		recipeCommand.setNotes(notesCommand);
		
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(CAT_ID_1);
		
		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand2.setId(CAT_ID_2);
		
		recipeCommand.getCategories().add(categoryCommand);
		recipeCommand.getCategories().add(categoryCommand2);
		
	    IngredientCommand ingredientCommand = new IngredientCommand();
	    ingredientCommand.setId(INGRED_ID_1);
	    
	    IngredientCommand ingredientCommand2 = new IngredientCommand();
	    ingredientCommand2.setId(INGRED_ID_2);
	    
	    recipeCommand.getIngredients().add(ingredientCommand);
	    recipeCommand.getIngredients().add(ingredientCommand2);
	    
	    //when
	    Recipe recipe = converter.convert(recipeCommand);
	    
	    
	    //then
	    assertNotNull(recipe);
	    assertEquals(RECIPE_ID, recipe.getId());
	    assertEquals(COOK_TIME, recipe.getCookTime());
	    assertEquals(PREP_TIME, recipe.getPrepTime());
	    assertEquals(DESCRIPTION, recipe.getDescription());
	    assertEquals(DIFFICULTY, recipe.getDifficulty());
	    assertEquals(DIRECTIONS, recipe.getDirections());
	    assertEquals(SERVINGS, recipe.getServings());
	    assertEquals(SOURCE, recipe.getSource());
	    assertEquals(URL, recipe.getUrl());
	    assertEquals(NOTES_ID, recipe.getNotes().getId());
	    assertEquals(2, recipe.getCategories().size());
	    assertEquals(2, recipe.getIngredients().size());    
		
	}

}
