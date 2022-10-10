package app.sbrecipeapp.services;

import app.sbrecipeapp.commands.IngredientCommand;

public interface IngredientService {
    
    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteById(Long recipeId, Long idToDelete);
}
