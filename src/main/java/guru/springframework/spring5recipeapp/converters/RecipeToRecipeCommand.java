/**
 * 
 */
package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.RecipeCommand;
import guru.springframework.spring5recipeapp.domain.Recipe;
import lombok.Synchronized;

/**
 * @author vijayakumar
 * @Since  14-Mar-2022
 *
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final NotesToNotesCommand notesConverter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final CategoryToCategoryCommand categoryConverter;
	
	RecipeToRecipeCommand(NotesToNotesCommand notesConverter, IngredientToIngredientCommand ingredientConverter, 
			CategoryToCategoryCommand categoryConverter) {
		this.notesConverter = notesConverter;
		this.ingredientConverter = ingredientConverter;
		this.categoryConverter = categoryConverter;
	}
	
	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null)
			return null;
		
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setNotesCommand(notesConverter.convert(source.getNotes()));
		
		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			recipeCommand.getIngredientsCommand()
			.addAll(source.getIngredients()
					.stream()
					.map(ingredient -> ingredientConverter.convert(ingredient))
					.toList());
		}
		
		recipeCommand.setDifficulty(source.getDifficulty());
		
		if (source.getCategories() != null && source.getCategories().size() > 0) {
			recipeCommand.getCategoriesCommand()
			.addAll(source.getCategories()
					.stream()
					.map(category -> categoryConverter.convert(category))
					.toList());
		}
		
		return recipeCommand;
	}

}
