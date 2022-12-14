package app.sbrecipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import app.sbrecipeapp.commands.RecipeCommand;
import app.sbrecipeapp.services.ImageService;
import app.sbrecipeapp.services.RecipeService;

public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    ImageController imageController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        imageController = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void testHandleImagePost() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                "MrDevSam".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile)).andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImgeFile(anyLong(), any());
    }

    @Test
    void testShowUploadForm() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        // when
        mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void testGetimgFromDb() throws Exception {
        // given
        RecipeCommand rCommand = new RecipeCommand();
        rCommand.setId(1L);

        String str = "fake image text";
        Byte[] boxedByte = new Byte[str.getBytes().length];

        int i = 0;

        for (byte primByte : str.getBytes()) {
            boxedByte[i++] = primByte;
        }

        rCommand.setImage(boxedByte);

        when(recipeService.findCommandById(anyLong())).thenReturn(rCommand);

        // when
        MockHttpServletResponse mServletResponse = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk()).andReturn().getResponse();

        byte[] responseBytes = mServletResponse.getContentAsByteArray();

        assertEquals(str.getBytes().length, responseBytes.length);
    }

    @Test
    void testGetImageNotFoundOnNumberFormat() throws Exception{

        mockMvc.perform(get("/recipe/addfs/recipeimage"))
        .andExpect(status().isBadRequest())
        .andExpect(view().name("400error"));
    }
}
