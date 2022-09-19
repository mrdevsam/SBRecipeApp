package app.sbrecipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import app.sbrecipeapp.commands.NotesCommand;
import app.sbrecipeapp.domain.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand>{

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

        final NotesCommand nCommand = new NotesCommand();
        nCommand.setId(source.getId());
        nCommand.setRecipeNotes(source.getRecipeNotes());
        return nCommand;
    }
    
}
