package app.sbrecipeapp.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.converters.RecipeCommandToRecipe;
import app.sbrecipeapp.converters.RecipeToRecipeCommand;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    
    
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe rCommandToRecipe, RecipeToRecipeCommand rToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = rCommandToRecipe;
        this.recipeToRecipeCommand = rToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Inside service");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long ln) {
        Optional<Recipe> rOptional = recipeRepository.findById(ln);

        if (!rOptional.isPresent()) {
            throw new RuntimeException("Recipe not found!!!");
        }

        return rOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand rCommand) {

        Recipe detachRecipe = recipeCommandToRecipe.convert(rCommand);
        Recipe savedRecipe = recipeRepository.save(detachRecipe);
        log.debug("Saved RecipeId: " + savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        return recipeToRecipeCommand.convert(findById(l));
    }

    @Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }

    
}
