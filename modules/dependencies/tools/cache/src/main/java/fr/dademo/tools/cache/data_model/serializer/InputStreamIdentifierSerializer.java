package fr.dademo.tools.cache.data_model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;

import java.io.IOException;

import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_CLASS;
import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_VALUE;

@SuppressWarnings({"java:S2055", "rawtypes"})
public class InputStreamIdentifierSerializer extends StdSerializer<InputStreamIdentifier> {

    private static final long serialVersionUID = 1796264361835867369L;

    public InputStreamIdentifierSerializer() {
        super(InputStreamIdentifier.class);
    }

    public InputStreamIdentifierSerializer(Class<InputStreamIdentifier> t) {
        super(t);
    }

    @Override
    public void serialize(InputStreamIdentifier value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField(FIELD_SERIALIZED_CLASS, value.getClass().getName());
        gen.writeObjectField(FIELD_SERIALIZED_VALUE, value);
        gen.writeEndObject();
    }
}
