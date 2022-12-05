package app.sbrecipeapp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import reactor.core.publisher.Flux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.services.IngredientService;
import app.sbrecipeapp.services.RecipeService;
import app.sbrecipeapp.services.UnitOfMeasureService;

public class IngredientControllerTest {

    IngredientController controller;
    MockMvc mockMvc;

    @Mock
    RecipeService rService;

    @Mock
    IngredientService iService;

    @Mock
    UnitOfMeasureService uService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new IngredientController(rService, iService, uService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListIngredients() throws Exception {
        // given
        RecipeCommand rCommand = new RecipeCommand();
        when(rService.findCommandById(anyString())).thenReturn(rCommand);

        // when
        mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

        // then
        verify(rService, times(1)).findCommandById(anyString());
    }

    @Test
    void testShowIngredient() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();

        // when
        when(iService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand);

        // then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void testUpdateIngredientForm() throws Exception {
        // given
        IngredientCommand ingredientCommand = new IngredientCommand();

        // when
        when(iService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(ingredientCommand);
        when(uService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        // then
        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        // given
        IngredientCommand command = new IngredientCommand();
        command.setId(String.valueOf("3"));
        command.setRecipeId(String.valueOf("2"));

        // when
        when(iService.saveIngredientCommand(any())).thenReturn(command);

        // then
        mockMvc.perform(post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    void testNewIngredientForm() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(String.valueOf("1"));

        // when
        when(rService.findCommandById(anyString())).thenReturn(recipeCommand);
        when(uService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

        // then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(rService, times(1)).findCommandById(anyString());
    }

    @Test
    public void testDeleteIngredient() throws Exception {

        // then
        mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredients"));

        verify(iService,times(1)).deleteById(anyString(), anyString());
    }
}
