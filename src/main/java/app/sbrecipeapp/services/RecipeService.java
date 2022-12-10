package app.sbrecipeapp.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.domain.Recipe;

public interface RecipeService {
    Flux<Recipe> getRecipes();

    Mono<Recipe> findById(String l);

    Mono<RecipeCommand> findCommandById(String l);
    
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand rCommand);

    Mono<Void> deleteById(String idToDelete);
}