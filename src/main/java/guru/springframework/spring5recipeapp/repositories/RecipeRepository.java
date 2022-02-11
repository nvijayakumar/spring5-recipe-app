/**
 * 
 */
package guru.springframework.spring5recipeapp.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring5recipeapp.domain.Recipe;

/**
 * @author vijayakumar
 * @Since  11-Feb-2022
 *
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
