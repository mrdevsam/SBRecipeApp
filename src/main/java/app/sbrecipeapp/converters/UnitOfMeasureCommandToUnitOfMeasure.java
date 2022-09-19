package app.sbrecipeapp.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure uMeasure = new UnitOfMeasure();
        uMeasure.setId(source.getId());
        uMeasure.setDescription(source.getDescription());
        return uMeasure;
    }
    
}
