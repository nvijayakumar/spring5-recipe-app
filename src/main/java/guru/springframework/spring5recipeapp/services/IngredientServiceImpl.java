/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.spring5recipeapp.commands.IngredientCommand;
import guru.springframework.spring5recipeapp.converters.IngredientCommandToIngredient;
import guru.springframework.spring5recipeapp.converters.IngredientToIngredientCommand;
import guru.springframework.spring5recipeapp.domain.Ingredient;
import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.IngredientRepository;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author vijayakumar
 * @Since  15-Apr-2022
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final IngredientRepository ingredientRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	
	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, UnitOfMeasureRepository unitOfMeasureRepository, 
			IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.ingredientRepository = ingredientRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		
		Optional<Ingredient> optional = ingredientRepository.findByIdAndRecipeId(ingredientId, recipeId);
		
		IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(optional.orElseThrow(() -> new RuntimeException("Ingredient cannot be null.")));
		
		log.debug("Ingredient - " + ingredientCommand);
		
		return ingredientCommand;
	}
	
	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
		
		if(!optionalRecipe.isPresent()) {
			log.error("No recipe found for Id: " + ingredientCommand.getId());
		}
		
		Recipe recipe = optionalRecipe.get();
		
		Optional<Ingredient> optionalIngredient = recipe.getIngredients()
			.stream()
			.filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
			.findFirst();
		
		if(optionalIngredient.isPresent()) {
			Ingredient ingredientFound = optionalIngredient.get();
			
			ingredientFound.setDescription(ingredientCommand.getDescription());
			ingredientFound.setAmount(ingredientCommand.getAmount());
			ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUomCommand().getId())
							.orElseThrow(() -> new RuntimeException("No UOM found.")));
		} else {
			recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
		}
		
		Recipe savedRecipe = recipeRepository.save(recipe);
		
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
				.filter(i -> i.getId().equals(ingredientCommand.getId()))
				.findFirst();
		
		//since ingredientCommand won't have the Id, we have to search by other member variables
		if (!savedIngredientOptional.isPresent()) {
			savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
					.filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
					.filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUomCommand().getId()))
					.findFirst();
		}
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}
	
	@Override
	public void deleteIngredientById(Long recipeId, Long ingredientId) {

		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		
		if(!optionalRecipe.isPresent()) {
			log.error("deleteIngredientCommand() - No recipe found for Id: " + recipeId);
		}
		
		log.debug("deleteIngredientById() - Ingredient Id: "+ ingredientId);
		
		if(optionalRecipe.isPresent()) {
			Recipe recipe = optionalRecipe.get();
			
			Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
					.filter(i -> i.getId().equals(ingredientId))
					.findFirst();
			
			if (optionalIngredient.isPresent()) {
				Ingredient ingredient = optionalIngredient.get();
				ingredient.setRecipe(null);
				recipe.getIngredients().remove(ingredient);
				recipeRepository.save(recipe);
			} else {
				log.info("No ingredient found.");
			}
		}
	}
}
