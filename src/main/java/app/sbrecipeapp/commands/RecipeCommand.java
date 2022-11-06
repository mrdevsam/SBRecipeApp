package app.sbrecipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import java.util.HashSet;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import app.sbrecipeapp.domain.Difficulty;


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
