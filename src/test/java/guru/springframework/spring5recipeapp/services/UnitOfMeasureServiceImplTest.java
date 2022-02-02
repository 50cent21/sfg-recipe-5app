package guru.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

class UnitOfMeasureServiceImplTest {

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand =
			new UnitOfMeasureToUnitOfMeasureCommand();
	
	UnitOfMeasureService unitOfMeasureService;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	@BeforeEach
	void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, 
				unitOfMeasureToUnitOfMeasureCommand);
		
	}

	@Test
	void testListAllUnitsOfMeasure() throws Exception{
		
		//given
		Set<UnitOfMeasure> unitsOfMeasure = new HashSet<>();
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		
		unitsOfMeasure.add(uom1);
		unitsOfMeasure.add(uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(unitsOfMeasure);
		
		//when
		Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUnitsOfMeasure();
		
		//then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}
