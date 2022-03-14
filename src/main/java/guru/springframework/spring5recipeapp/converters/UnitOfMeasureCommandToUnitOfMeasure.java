/**
 * 
 */
package guru.springframework.spring5recipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;

/**
 * @author vijayakumar
 * @Since  13-Mar-2022
 *
 */
@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if (source == null)
			return null;
		
		final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(source.getId());
		unitOfMeasure.setUom(source.getUom());
		
		return unitOfMeasure;
	}

}
