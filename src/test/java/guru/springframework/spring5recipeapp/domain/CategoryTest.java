package guru.springframework.spring5recipeapp.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

	Category category;
	
	@BeforeEach
	void setUp() throws Exception {
		
		category = new Category();
	}

	@Test
	void testGetId() {
		
		Long valueId = 4l;
		
		category.setId(valueId);
		
		assertEquals(valueId, category.getId());
		
	}

}
