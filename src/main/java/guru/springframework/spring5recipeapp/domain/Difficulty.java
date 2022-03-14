/**
 * 
 */
package guru.springframework.spring5recipeapp.domain;

/**
 * @author vijayakumar
 * @Since  10-Feb-2022
 *
 */
public enum Difficulty {

	EASY("Easy"), MODERATE("Moderate"), HARD("Hard");
	
	String value;
	
	Difficulty(String value) {
		this.value = value;
	}
	
	String getValue() {
		return value;
	}
}
