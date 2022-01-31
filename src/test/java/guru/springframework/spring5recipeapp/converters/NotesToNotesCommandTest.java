package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;

class NotesToNotesCommandTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String RECIPE_NOTES = "Notes";
	NotesToNotesCommand converter;
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesToNotesCommand();
	}
	
	@Test
	void testNullObject() {
		assertNull(converter.convert(null));
	}
	
	@Test
	void testEmptyObject() throws Exception{
		assertNotNull(converter.convert(new Notes()));
	}

	@Test
	void testConvert() {
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);
		
		NotesCommand notesCommand = converter.convert(notes);
		
		assertEquals(ID_VALUE, notesCommand.getId());
		assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
	}

}
