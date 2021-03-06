/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import java.util.Set;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;

/**
 * @author vijayakumar
 * @Since  12-Feb-2022
 *
 */
public interface RecipeService {

	Set<Recipe> getRecipes();
	
	Recipe findById(Long recipeId);
	
	RecipeCommand findCommandById(Long recipeId);
	
	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
	
	void deleteById(Long recipeId);
}
