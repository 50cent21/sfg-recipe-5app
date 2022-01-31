package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.NotesCommand;
import guru.springframework.spring5recipeapp.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	
	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes source) {
		
		if(source != null) {
			
			final NotesCommand notesCommand = new NotesCommand();
			notesCommand.setId(source.getId());
			notesCommand.setRecipeNotes(source.getRecipeNotes());
			return notesCommand;
		}
		
		return null;
	}

}
