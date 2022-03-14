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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final NotesCommandToNotes notesConverter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final CategoryCommandToCategory categoryConverter;
	
	RecipeCommandToRecipe(NotesCommandToNotes notesConverter, IngredientCommandToIngredient ingredientConverter, 
			CategoryCommandToCategory categoryConverter) {
		this.notesConverter = notesConverter;
		this.ingredientConverter = ingredientConverter;
		this.categoryConverter = categoryConverter;
	}
	
	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null)
			return null;
		
		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setNotes(notesConverter.convert(source.getNotesCommand()));
		
		if (source.getIngredientsCommand() != null && source.getIngredientsCommand().size() > 0) {
			recipe.getIngredients()
			.addAll(source.getIngredientsCommand()
					.stream()
					.map(ingredientCommand -> ingredientConverter.convert(ingredientCommand))
					.toList());
		}
		
		recipe.setDifficulty(source.getDifficulty());
		
		if (source.getCategoriesCommand() != null && source.getCategoriesCommand().size() > 0) {
			recipe.getCategories()
			.addAll(source.getCategoriesCommand()
					.stream()
					.map(categoryCommand -> categoryConverter.convert(categoryCommand))
					.toList());
		}
		
		return recipe;
	}

}
