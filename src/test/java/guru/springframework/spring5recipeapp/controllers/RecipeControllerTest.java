/**
 * 
 */
package guru.springframework.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.exceptions.NotFoundException;
import guru.springframework.spring5recipeapp.services.RecipeService;

/**
 * @author vijayakumar
 * @Since  08-Mar-2022
 *
 */
class RecipeControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;

	@Captor
	ArgumentCaptor<Long> captor;
	
	RecipeController recipeController;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
					.setControllerAdvice(new ControllerExceptionHandler())
					.build();
	}

	@Test
	void testShowById() throws Exception {
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipe1.setDescription("1");

		when(recipeService.findById(anyLong())).thenReturn(recipe1);
		
		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(view().name("/recipe/show"))
			.andExpect(model().attributeExists("recipe"))
			.andExpect(status().isOk());
		
		verify(recipeService, times(1)).findById(captor.capture());
		
		Long recipeId = captor.getValue();
		assertEquals(1L, recipeId);
	}

	@Test
	void testDeleteById() throws Exception {
		mockMvc.perform(get("/recipe/2/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
		
		verify(recipeService, times(1)).deleteById(anyLong());
	}
	
	@Test
	void testGetRecipeNotFound() throws Exception {
		when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
			.andExpect(status().isNotFound())
			.andExpect(view().name("404error"));
	}
	
	@Test
	void testGetRecipeNumberFormat() throws Exception {
		mockMvc.perform(get("/recipe/1asdf/show"))
			.andExpect(status().isBadRequest())
			.andExpect(view().name("400error"));
	}
	
    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "").param("description", "some desc").param("directions", "some directions"))
        	.andExpect(status().is3xxRedirection())
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("redirect:/recipe/2/show"));
    }
}
