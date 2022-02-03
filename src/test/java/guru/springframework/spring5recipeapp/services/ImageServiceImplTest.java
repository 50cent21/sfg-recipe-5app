package guru.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	void testSaveImageFile() throws Exception{
		
		//given
		Long id = 1L;
		MockMultipartFile multipartFile = new MockMultipartFile(
				"imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//when
		imageService.saveImageFile(id, multipartFile);
		
		//then
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
