/**
 * 
 */
package guru.springframework.spring5recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring5recipeapp.domain.Ingredient;

/**
 * @author vijayakumar
 * @Since  15-Apr-2022
 *
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

	Optional<Ingredient> findByIdAndRecipeId(Long id, Long recipeId);
}
