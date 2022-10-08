package app.sbrecipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.domain.Ingredient;
import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand>{
    private final UnitOfMeasureToUnitOfMeasureCommand uomCommand;
    
    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomCommand) {
        this.uomCommand = uomCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

        IngredientCommand ingCommand = new IngredientCommand();
        
        ingCommand.setId(source.getId());

        if (source.getRecipe() != null) {
            ingCommand.setRecipeId(source.getRecipe().getId());
        }
        
        ingCommand.setDescription(source.getDescription());
        ingCommand.setAmount(source.getAmount());
        ingCommand.setUom(uomCommand.convert(source.getUom()));
        return ingCommand;
    }
    
}
