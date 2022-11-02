package app.sbrecipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.HashSet;

import org.hibernate.validator.constraints.URL;

import app.sbrecipeapp.domain.Difficulty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private Byte[] image;
    
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();

    private NotesCommand notes;
    private Difficulty difficulty;
}
