package app.sbrecipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.converters.RecipeCommandToRecipe;
import app.sbrecipeapp.converters.RecipeToRecipeCommand;
import app.sbrecipeapp.domain.Recipe;
import app.sbrecipeapp.repositories.reactive.RecipeReactiveRepository;

public class RecipeServiceImplTest {

    RecipeServiceImpl rServiceImpl;

    @Mock
    RecipeReactiveRepository repository;//declaring the repository as a Mock

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
        recipe.setId("1");

        when(repository.findById(anyString())).thenReturn(Mono.just(recipe));

        Recipe recipeReturned = rServiceImpl.findById("1").block();

        assertNotNull(recipeReturned, "null recipe returned");
        verify(repository,times(1)).findById(anyString());
        verify(repository,never()).findAll();//verifing
    }

    @Test
    void testGetRecipes() throws Exception{
        Recipe recipe = new Recipe();
        HashSet rData = new HashSet();
        rData.add(recipe);

        when(repository.findAll()).thenReturn(Flux.just(recipe));

        List<Recipe> recipes = rServiceImpl.getRecipes().collectList().block();

        assertEquals(recipes.size(), 1);
        verify(repository,times(1)).findAll();
        verify(repository,never()).findById(anyString());//verifing
    }

    @Test
    public void getRecipeCommandByIdTest() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId("1");

        when(repository.findById(anyString())).thenReturn(Mono.just(recipe));

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId("1");

        when(rToRecipeCommand.convert(any())).thenReturn(recipeCommand);

        RecipeCommand commandById = rServiceImpl.findCommandById("1").block();

        assertNotNull( commandById, "Null recipe returned");
        verify(repository, times(1)).findById(anyString());
        verify(repository, never()).findAll();
    }

    @Test
    void testDeleteById() throws Exception{
        //given
        String idToDelete = String.valueOf(2L);

        when(repository.deleteById(anyString())).thenReturn(Mono.empty());

        //when
        rServiceImpl.deleteById(idToDelete);

        //no 'when' statement since method has void return type

        //then
        verify(repository, times(1)).deleteById(anyString());
    }
    
}
    
