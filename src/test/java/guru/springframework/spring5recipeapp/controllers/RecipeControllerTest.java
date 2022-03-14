/**
 * 
 */
package guru.springframework.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.spring5recipeapp.domain.Recipe;
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
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		recipeController = new RecipeController(recipeService);
	}

	@Test
	void testShowById() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipe1.setDescription("1");

		when(recipeService.findById(anyLong())).thenReturn(recipe1);
		
		mockMvc.perform(get("/recipe/show/1"))
			.andExpect(view().name("/recipe/show"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
			.andExpect(status().isOk());
		
		verify(recipeService, times(1)).findById(captor.capture());
		
		Long recipeId = captor.getValue();
		assertEquals(1L, recipeId);
	}

}
