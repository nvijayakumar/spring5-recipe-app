/**
 * 
 */
package guru.springframework.spring5recipeapp.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import guru.springframework.spring5recipeapp.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vijayakumar
 * @Since  13-Mar-2022
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	
	@NotBlank(message = "Description must be 3 character minimum")
	@Size(min = 3, max = 255)
	private String description;
	
	@Min(1)
	@Max(999)
	private Integer prepTime;
	
	@Min(1)
	@Max(999)
	private Integer cookTime;
	
	@Min(1)
	@Max(100)
	private Integer servings;
	
	private String source;
	
	@URL
	private String url;
	
	@NotBlank(message = "Directions is mandatory")
	private String directions;
	
	private Byte[] image;

	private NotesCommand notesCommand;

	private Set<IngredientCommand> ingredientsCommand = new HashSet<>();

	private Difficulty difficulty;

	private Set<CategoryCommand> categoriesCommand = new HashSet<>();
}
