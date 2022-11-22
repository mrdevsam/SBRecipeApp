package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.converters.RecipeCommandToRecipe;
import app.sbrecipeapp.converters.RecipeToRecipeCommand;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceImplIT {

    public static final String NEW_DESCRIPTION = "New Description";

    @Autowired
    RecipeRepository recipeRepository;
    
    @Autowired
    RecipeService recipeService;
    
    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;
    
    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    //@Transactional
    @Test
    void testSaveOfDescription() throws Exception{
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
