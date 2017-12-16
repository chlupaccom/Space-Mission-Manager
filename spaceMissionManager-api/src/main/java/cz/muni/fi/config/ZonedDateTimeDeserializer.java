package cz.muni.fi.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * Created by jsmadis
 *
 * @author jsmadis
 */

public class ZonedDateTimeDeserializer extends StdDeserializer<ZonedDateTime> {
    public ZonedDateTimeDeserializer() {
        super(ZonedDateTime.class);
    }


    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return ZonedDateTime.parse(jsonParser.readValueAs(String.class));
    }
}



