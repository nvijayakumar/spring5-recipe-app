/**
 * 
 */
package guru.springframework.spring5recipeapp.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;

/**
 * @author vijayakumar
 * @Since  22-Feb-2022
 *
 */
@DataJpaTest
class UnitOfMeasureRepositoryITest {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByUom() {
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByUom("Cup");
		
		assertEquals("Cup", uom.get().getUom());
		
		Optional<UnitOfMeasure> uom1 = unitOfMeasureRepository.findByUom("Teaspoon");
		
		assertEquals("Teaspoon", uom1.get().getUom());
	}

}
