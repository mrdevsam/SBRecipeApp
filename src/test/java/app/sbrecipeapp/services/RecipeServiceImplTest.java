package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.converters.RecipeCommandToRecipe;
import app.sbrecipeapp.converters.RecipeToRecipeCommand;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.RecipeRepository;

public class RecipeServiceImplTest {

    RecipeServiceImpl rServiceImpl;

    @Mock
    RecipeRepository repository;//declaring the repository as a Mock

    @Mock
    RecipeCommandToRecipe rCommandToRecipe;

    @Mock
    RecipeToRecipeCommand rToRecipeCommand;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);//initializing Mokito Mock

        rServiceImpl = new RecipeServiceImpl(repository, rCommandToRecipe, rToRecipeCommand);
    } 

    @Test
    void testGetRecipeById() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> rOptional = Optional.of(recipe);

        when(repository.findById(anyLong())).thenReturn(rOptional);

        Recipe recipeReturned = rServiceImpl.findById(1L);

        assertNotNull(recipeReturned, "null recipe returned");
        verify(repository,times(1)).findById(anyLong());
        verify(repository,never()).findAll();//verifing
    }

    @Test
    void testGetRecipes() throws Exception{
        Recipe recipe = new Recipe();
        HashSet rData = new HashSet();
        rData.add(recipe);

        when(repository.findAll()).thenReturn(rData);

        Set<Recipe> recipes = rServiceImpl.getRecipes();

        assertEquals(recipes.size(), 1);
        verify(repository,times(1)).findAll();
        verify(repository,never()).findById(anyLong());//verifing
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(repository.findById(anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(rToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = rServiceImpl.findCommandById(1L);

        assertNotNull( commandById, "Null recipe returned");
        verify(repository, times(1)).findById(anyLong());
        verify(repository, never()).findAll();
    }

    @Test
    void testDeleteById() throws Exception{
        //given
        Long idToDelete = Long.valueOf(2L);

        //when
        rServiceImpl.deleteById(idToDelete);

        //no 'when' statement since method has void return type

        //then
        verify(repository, times(1)).deleteById(anyLong());
    }
    
}
    
