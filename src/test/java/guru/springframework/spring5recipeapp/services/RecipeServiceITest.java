/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.converters.RecipeCommandToRecipe;
import guru.springframework.spring5recipeapp.converters.RecipeToRecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

/**
 * @author vijayakumar
 * @Since  14-Mar-2022
 *
 */
@SpringBootTest
public class RecipeServiceITest {

	private final String NEW_DESCRIPTION = "New Description";
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Transactional
	@Test
	void testSaveOfDescription() {
		//given
		Iterable<Recipe> iterable = recipeRepository.findAll();
		Recipe recipe = iterable.iterator().next();
		RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
		
		//when
		recipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		//then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(recipe.getId(), savedRecipeCommand.getId());
		assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategoriesCommand().size());
		assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredientsCommand().size());
	}
}
