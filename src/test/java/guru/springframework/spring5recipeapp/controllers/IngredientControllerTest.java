package guru.springframework.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.IngredientService;
import guru.springframework.spring5recipeapp.services.RecipeService;
import guru.springframework.spring5recipeapp.services.UnitOfMeasureService;

class IngredientControllerTest {

	@Mock
	IngredientService ingredientService;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	UnitOfMeasureService unitOfMeasureService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		controller = new IngredientController(ingredientService, recipeService, unitOfMeasureService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testListIngredients() throws Exception{
		
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("recipe/ingredient/list"))
		       .andExpect(model().attributeExists("recipe"));
		       
		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}
	
	@Test
	void testShowIngredient() throws Exception {
		
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("recipe/ingredient/show"))
		       .andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	void testUpdateIngredientForm() throws Exception {
		
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAllUnitsOfMeasure()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("recipe/ingredient/ingredientform"))
		       .andExpect(model().attributeExists("ingredient"))
		       .andExpect(model().attributeExists("uomList"));
		
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		//when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
		
		//then
		mockMvc.perform(post("/recipe/2/ingredient")
			   .contentType(MediaType.APPLICATION_FORM_URLENCODED)
			   .param("id", "")
			   .param("description", "some string")
        )
		       .andExpect(status().is3xxRedirection())
		       .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
		
	}

}
