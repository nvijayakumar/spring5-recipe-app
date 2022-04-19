/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import java.util.Set;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;

/**
 * @author vijayakumar
 * @Since  15-Apr-2022
 *
 */
public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
}
