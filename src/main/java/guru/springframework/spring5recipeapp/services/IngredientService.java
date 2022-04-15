/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;

/**
 * @author vijayakumar
 * @Since  15-Apr-2022
 *
 */
public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
