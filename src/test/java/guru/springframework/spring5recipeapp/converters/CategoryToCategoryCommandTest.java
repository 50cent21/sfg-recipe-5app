package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;

class CategoryToCategoryCommandTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String DESCRIPTION = "description";
	CategoryToCategoryCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		
		converter = new CategoryToCategoryCommand();
	}
	
	@Test
	public void testNullObject() throws Exception{
		
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		
		assertNotNull(converter.convert(new Category()));
	}

	@Test
	void testConvert() {
		//given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);
		
		//when
		CategoryCommand categoryCommand = converter.convert(category);
		
		//then
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
		
		
	}

}
