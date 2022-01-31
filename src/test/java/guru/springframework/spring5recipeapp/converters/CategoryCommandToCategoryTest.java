package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.CategoryCommand;
import guru.springframework.spring5recipeapp.domain.Category;

class CategoryCommandToCategoryTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String DESCRIPTION = "description";
	CategoryCommandToCategory converter;
	
	@BeforeEach
	void setUp() throws Exception {
	     converter = new CategoryCommandToCategory();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test 
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	
	@Test
	void testConvert() throws Exception{
		//given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);
		
		//when
		Category category = converter.convert(categoryCommand);
		
		//then
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}

}
