package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;

class UnitOfMeasureToUnitOfMeasureCommandTest {

	private static final Long ID_VALUE = new Long(1L);
	private static final String DESCRIPTION = "description";
	UnitOfMeasureToUnitOfMeasureCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}
	
	@Test
	void testConvert() {
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(ID_VALUE);
		unitOfMeasure.setDescription(DESCRIPTION);
		
		UnitOfMeasureCommand unitOfMeasureCommand = converter.convert(unitOfMeasure);
		
		assertEquals(ID_VALUE, unitOfMeasureCommand.getId());
		assertEquals(DESCRIPTION, unitOfMeasureCommand.getDescription());
	}

}
