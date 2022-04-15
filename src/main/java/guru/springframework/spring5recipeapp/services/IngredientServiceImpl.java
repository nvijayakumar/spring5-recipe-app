/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vijayakumar
 * @Since  15-Apr-2022
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientRepository ingredientRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	
	public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientToIngredientCommand ingredientToIngredientCommand) {
		this.ingredientRepository = ingredientRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		
		Optional<Ingredient> optional = ingredientRepository.findByIdAndRecipeId(ingredientId, recipeId);
		
		IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(optional.orElseThrow(() -> new RuntimeException("Ingredient cannot be null.")));
		
		log.debug("Ingredient - " + ingredientCommand);
		
		return ingredientCommand;
	}
}
