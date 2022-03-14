/**
 * 
 */
package guru.springframework.spring5recipeapp.commands;

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
public class CategoryCommand {

	private Long id;
	
	private String description;
	
}
