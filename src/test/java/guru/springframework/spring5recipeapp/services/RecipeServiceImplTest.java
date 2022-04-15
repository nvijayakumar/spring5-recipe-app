/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
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

import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

/**
 * @author vijayakumar
 * @Since  19-Feb-2022
 *
 */
class RecipeServiceImplTest {

	RecipeServiceImpl recipeServiceImpl;
	
	//Mock will create a dummy object with same signature and do noting.
	//just return the default value for primitive types and null for custom object.
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		recipeServiceImpl = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	void testGetRecipes() {
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
		
		recipeRepository.findAll().forEach(System.out::println);
		System.out.println(recipeRepository.count());
		System.out.println("save : " + recipeRepository.save(recipe3));
		
		//when the mock unit is called then return this value.
		when(recipeRepository.findAll()).thenReturn(recipes);
		
		Set<Recipe> set = recipeServiceImpl.getRecipes();
		
		assertEquals(3, set.size());
		
		//this will verify how many times the mock object was called. Specifically findAll() method.
		verify(recipeRepository, times(2)).findAll();
	}
	
	@Test
	void testFindById() {
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		recipe1.setDescription("1");
		
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe1));
		
		Recipe returnRecipe = recipeServiceImpl.findById(1L);
		
		assertEquals(1, returnRecipe.getId());
		
		verify(recipeRepository, times(1)).findById(anyLong());
		
		verify(recipeRepository, never()).findAll();
	}

	@Test
	void testDeleteById() {
		Long recipeId = 2l;
		//when
		recipeServiceImpl.deleteById(recipeId);
		
		verify(recipeRepository, times(1)).deleteById(anyLong());
	}
}
