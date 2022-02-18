/**
 * 
 */
package guru.springframework.spring5recipeapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author vijayakumar
 * @Since  09-Feb-2022
 *
 */
@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Recipe recipe;
	
	/**
	 * The recipe notes should be long and need more
	 * space to store. Usually DB provides 255 character 
	 * size by default.
	 */
	@Lob
	private String recipeNotes;
	
}
