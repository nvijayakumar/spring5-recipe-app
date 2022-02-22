/**
 * 
 */
package guru.springframework.spring5recipeapp.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.services.RecipeService;

/**
 * @author vijayakumar
 * @Since  22-Feb-2022
 *
 */
class IndexControllerTest {

	IndexController indexController;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	@Captor
	ArgumentCaptor<Set<Recipe>> argumentCaptor;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		indexController = new IndexController(recipeService);
	}

	@Test
	void testMockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
			.andExpect(MockMvcResultMatchers.status().isOk()) //check whether the http status is OK.
			.andExpect(MockMvcResultMatchers.view().name("index")) //check whether the controller returns "index" view name.
			;
	}
	
	@Test
	void getIndexPage() {
		
		//given
		Set<Recipe> recipes = new HashSet<Recipe>();
		Recipe recipe1 = new Recipe();
		recipe1.setDescription("1");
		
		Recipe recipe2 = new Recipe();
		recipe2.setDescription("2");
		
		Recipe recipe3 = new Recipe();
		recipe3.setDescription("3");
		
		recipes.add(recipe1);
		recipes.add(recipe2);
		recipes.add(recipe3);
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		
		//when
		String viewName = indexController.getIndexPage(model);
		
		//then
		
		//check the return value is correct
		assertEquals("index", viewName);
		
		//verify the RecipeService.getRecipes() method invoked one time.
		verify(recipeService, times(1)).getRecipes();
		
		//verify the Model.addAttribute("recipes", {recipe set}) method invoked one time.
		//And capture the argument passed to the model object.
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		
		//get the captured recipe set object and check the size.
		Set<Recipe> captureRecipes = argumentCaptor.getValue();
		assertEquals(3, captureRecipes.size());
	}

}
