/**
 * 
 */
package guru.springframework.spring5recipeapp.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;

/**
 * @author vijayakumar
 * @Since  11-Feb-2022
 *
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long>{
	
	Optional<UnitOfMeasure> findByUom(String uom);

}
