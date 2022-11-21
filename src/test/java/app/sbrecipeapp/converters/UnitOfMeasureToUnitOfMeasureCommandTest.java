package app.sbrecipeapp.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.sbrecipeapp.commands.UnitOfMeasureCommand;
import app.sbrecipeapp.domain.UnitOfMeasure;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
    
    public static final String DESCRIPTION = "description";
    public static final String String_VALUE = new String("1");

    UnitOfMeasureToUnitOfMeasureCommand converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(String_VALUE);
        uom.setDescription(DESCRIPTION);
        
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(String_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}
