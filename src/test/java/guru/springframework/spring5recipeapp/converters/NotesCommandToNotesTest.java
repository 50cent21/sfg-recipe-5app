package guru.springframework.spring5recipeapp.converters;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;

class NotesCommandToNotesTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String RECIPE_NOTES = "Notes";
	NotesCommandToNotes converter;
	
	
	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesCommandToNotes();
	}

	@Test
	public void testNullObject() throws Exception{
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(converter.convert(new NotesCommand()));
	}
	
	@Test
	void testConvert() {
		
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(ID_VALUE);
		notesCommand.setRecipeNotes(RECIPE_NOTES);
		
		Notes notes = converter.convert(notesCommand);
		
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
	}

}
