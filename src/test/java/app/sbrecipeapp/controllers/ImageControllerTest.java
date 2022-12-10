package app.sbrecipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import reactor.core.publisher.Mono;

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

        verify(imageService, times(1)).saveImgeFile(anyString(), any());
    }

    @Test
    void testShowUploadForm() throws Exception {
        // given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(String.valueOf("1"));

        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

        // when
        mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyString());
    }

    @Test
    void testGetimgFromDb() throws Exception {
        // given
        RecipeCommand rCommand = new RecipeCommand();
        rCommand.setId(String.valueOf("1"));

        String str = "fake image text";
        Byte[] boxedByte = new Byte[str.getBytes().length];

        int i = 0;

        for (byte primByte : str.getBytes()) {
            boxedByte[i++] = primByte;
        }

        rCommand.setImage(boxedByte);

        when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(rCommand));

        // when
        MockHttpServletResponse mServletResponse = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk()).andReturn().getResponse();

        byte[] responseBytes = mServletResponse.getContentAsByteArray();

        assertEquals(str.getBytes().length, responseBytes.length);
    }

}
