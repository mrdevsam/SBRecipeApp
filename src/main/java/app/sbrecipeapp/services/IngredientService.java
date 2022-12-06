package app.sbrecipeapp.services;

import app.sbrecipeapp.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    
    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
    Mono<Void> deleteById(String recipeId, String idToDelete);
}
