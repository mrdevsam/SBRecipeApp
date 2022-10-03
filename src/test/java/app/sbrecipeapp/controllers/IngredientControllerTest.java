package app.sbrecipeapp.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.sbrecipeapp.commands.IngredientCommand;
import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.services.IngredientService;
import app.sbrecipeapp.services.RecipeService;

public class IngredientControllerTest {

    IngredientController controller;
    MockMvc mockMvc;

    @Mock
    RecipeService rService;

    @Mock
    IngredientService iService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new IngredientController(rService, iService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testListIngredients() throws Exception {
        // given
        RecipeCommand rCommand = new RecipeCommand();
        when(rService.findCommandById(anyLong())).thenReturn(rCommand);

        // when
        mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

        //then
        verify(rService, times(1)).findCommandById(anyLong());
    }

    @Test
    void testShowIngredient() throws Exception{
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(iService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
        .andExpect(status().isOk())
        .andExpect(view().name("recipe/ingredient/show"))
        .andExpect(model().attributeExists("ingredient"));
    }

    
}
