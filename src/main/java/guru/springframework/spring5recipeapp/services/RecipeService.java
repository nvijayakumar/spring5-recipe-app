/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import java.util.Set;

import guru.springframework.spring5recipeapp.domain.Recipe;

/**
 * @author vijayakumar
 * @Since  12-Feb-2022
 *
 */
public interface RecipeService {

	Set<Recipe> getRecipes();
}
