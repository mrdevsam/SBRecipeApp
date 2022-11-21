package app.sbrecipeapp.services;

import app.sbrecipeapp.commands.IngredientCommand;

public interface IngredientService {
    
    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    void deleteById(String recipeId, String idToDelete);
}
