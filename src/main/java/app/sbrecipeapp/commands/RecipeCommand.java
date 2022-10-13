package app.sbrecipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.HashSet;

import app.sbrecipeapp.domain.Difficulty;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    
    private Long id;

    private String description;
    private String source;
    private String url;
    private String directions;

    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;

    private Byte[] image;
    
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();

    private NotesCommand notes;
    private Difficulty difficulty;
}
