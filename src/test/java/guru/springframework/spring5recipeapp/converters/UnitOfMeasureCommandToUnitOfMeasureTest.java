package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;

class UnitOfMeasureCommandToUnitOfMeasureTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String DESCRIPTION = "description";
	UnitOfMeasureCommandToUnitOfMeasure converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}
	
	@Test
	void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}

	@Test
	void testConvert() {
		
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(ID_VALUE);
		unitOfMeasureCommand.setDescription(DESCRIPTION);
		
		UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);
		
		assertEquals(ID_VALUE, unitOfMeasure.getId());
		assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
		
	}

}
