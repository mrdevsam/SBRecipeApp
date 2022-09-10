package app.sbrecipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import app.sbrecipeapp.services.RecipeService;

public class IndexControllerTest {
    IndexController iController;

    @Mock
    RecipeService rService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        iController = new IndexController(rService);
    }

    @Test
    void testGetIndexpage() {

        String iValue = iController.getIndexpage(model);

        assertEquals("index", iValue);
        verify(rService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}
