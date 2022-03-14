/**
 * 
 */
package guru.springframework.spring5recipeapp.commands;

import java.math.BigDecimal;

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
public class IngredientCommand {

	private Long id;
	
	private String description;
	
	private BigDecimal amount;
	
	private UnitOfMeasureCommand uomCommand;
}
