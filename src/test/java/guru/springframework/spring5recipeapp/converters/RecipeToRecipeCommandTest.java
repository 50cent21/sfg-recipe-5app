package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Category;
import guru.springframework.spring5recipeapp.domain.Difficulty;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Notes;
import guru.springframework.spring5recipeapp.domain.Recipe;

class RecipeToRecipeCommandTest {

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
	
	RecipeToRecipeCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		
		converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new NotesToNotesCommand());
	}
	
	@Test
	void testNullObject() throws Exception {
		
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Recipe()));
	}

	@Test
	void testConvert() {
		
		//given
		Recipe recipe = new Recipe();
		recipe.setId(RECIPE_ID);
		recipe.setCookTime(COOK_TIME);
		recipe.setPrepTime(PREP_TIME);
		recipe.setDescription(DESCRIPTION);
		recipe.setDifficulty(DIFFICULTY);
		recipe.setDirections(DIRECTIONS);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);
		
		Notes notes = new Notes();
		notes.setId(NOTES_ID);
		
		recipe.setNotes(notes);
		
		Category category = new Category();
		category.setId(CAT_ID_1);
		
		Category category2 = new Category();
		category2.setId(CAT_ID_2);
		
		recipe.getCategories().add(category);
		recipe.getCategories().add(category2);
		
		Ingredient ingredient = new Ingredient();
		ingredient.setId(INGRED_ID_1);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(INGRED_ID_2);
		
		recipe.getIngredients().add(ingredient);
		recipe.getIngredients().add(ingredient2);
		
		//when
		RecipeCommand recipeCommand = converter.convert(recipe);
		
		
		//then
		assertNotNull(recipeCommand);
	    assertEquals(RECIPE_ID, recipeCommand.getId());
	    assertEquals(COOK_TIME, recipeCommand.getCookTime());
	    assertEquals(PREP_TIME, recipeCommand.getPrepTime());
	    assertEquals(DESCRIPTION, recipeCommand.getDescription());
	    assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
	    assertEquals(DIRECTIONS, recipeCommand.getDirections());
	    assertEquals(SERVINGS, recipeCommand.getServings());
	    assertEquals(SOURCE, recipeCommand.getSource());
	    assertEquals(URL, recipeCommand.getUrl());
	    assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
	    assertEquals(2, recipeCommand.getCategories().size());
	    assertEquals(2, recipeCommand.getIngredients().size());
	}

}
