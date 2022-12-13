package app.sbrecipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    
    private String id;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand uom;
    
    private String recipeId;
}
