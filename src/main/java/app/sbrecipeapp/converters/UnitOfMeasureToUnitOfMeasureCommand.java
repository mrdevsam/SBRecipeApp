package app.sbrecipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source != null) {
            final UnitOfMeasureCommand unitOfMeasure = new UnitOfMeasureCommand();
            unitOfMeasure.setId(source.getId());
            unitOfMeasure.setDescription(source.getDescription());
            return unitOfMeasure;
        }

        return null;
    }

}
