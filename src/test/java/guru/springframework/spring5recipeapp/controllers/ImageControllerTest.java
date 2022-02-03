package guru.springframework.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.services.ImageService;
import guru.springframework.spring5recipeapp.services.RecipeService;

class ImageControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	ImageService imageService;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}

	@Test
	void testShowUploadForm() throws Exception{
		
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipe/1/image"))
		       .andExpect(status().isOk())
		       .andExpect(view().name("recipe/imageuploadform"));
		
		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
		
	}

	@Test
	void testHandleImagePost() throws Exception{
		
		MockMultipartFile multipartFile = new MockMultipartFile(
				"imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}
	
	@Test
	void testRenderImageFromDB() throws Exception {
		
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		String s = "fake image text";
		Byte[] bytesBoxed = new Byte[s.getBytes().length];
		
		int i = 0;
		
		for(byte primByte : s.getBytes()) {
			bytesBoxed[i++] = primByte;
		}
		
		recipeCommand.setImage(bytesBoxed);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		//then
		assertEquals(s.getBytes().length, responseBytes.length);
		
	}
	

}
