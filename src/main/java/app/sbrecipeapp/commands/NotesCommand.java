package app.sbrecipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    
    private String id;
    private String recipeNotes;
}
