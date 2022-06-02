/**
 * 
 */
package guru.springframework.spring5recipeapp.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author vijayakumar
 * @Since  25-Apr-2022
 *
 */
public interface ImageService {

	void saveImageFile(Long recipeId, MultipartFile multipartFile);
}
