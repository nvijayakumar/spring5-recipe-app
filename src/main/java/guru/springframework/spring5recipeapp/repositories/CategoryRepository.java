/**
 * 
 */
package guru.springframework.spring5recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring5recipeapp.domain.Category;

/**
 * @author vijayakumar
 * @Since  11-Feb-2022
 *
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);
}
