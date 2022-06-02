/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.spring5recipeapp.domain.Recipe;
import guru.springframework.spring5recipeapp.repositories.RecipeRepository;

/**
 * @author vijayakumar
 * @Since  28-Apr-2022
 *
 */
class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;
	
	@Captor
	ArgumentCaptor<Recipe> argumentCaptor;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	void testSaveImageFile() throws Exception {
		//given
		Long id = 1L;
		MultipartFile file = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(id);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//when
		imageService.saveImageFile(id, file);
		
		//verify
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe captureRecipe = argumentCaptor.getValue();
		assertEquals(file.getBytes().length, captureRecipe.getImage().length);
	}

}
