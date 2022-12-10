package app.sbrecipeapp.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.converters.RecipeCommandToRecipe;
import app.sbrecipeapp.converters.RecipeToRecipeCommand;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    
    
    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe rCommandToRecipe, RecipeToRecipeCommand rToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = rCommandToRecipe;
        this.recipeToRecipeCommand = rToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("Inside service");
        return recipeReactiveRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String ln) {
        return recipeReactiveRepository.findById(ln);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand rCommand) {

        return recipeReactiveRepository.save(recipeCommandToRecipe.convert(rCommand)).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String l) {

        return recipeReactiveRepository.findById(l).map(recipe -> {
            RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });
            return recipeCommand;
        });
    }

    @Override
    public Mono<Void> deleteById(String idToDelete) {
        recipeReactiveRepository.deleteById(idToDelete).block();
        return Mono.empty();
    }

    
}
