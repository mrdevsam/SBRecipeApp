package app.sbrecipeapp.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
    private Long recipeId;
}
