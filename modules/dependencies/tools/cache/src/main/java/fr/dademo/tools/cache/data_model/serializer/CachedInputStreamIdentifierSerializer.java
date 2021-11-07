package fr.dademo.tools.cache.data_model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;

import java.io.IOException;

import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_CLASS;
import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_VALUE;

@SuppressWarnings({"java:S2055", "rawtypes"})
public class CachedInputStreamIdentifierSerializer extends StdSerializer<CachedInputStreamIdentifier> {

    private static final long serialVersionUID = 1796264361835867369L;

    public CachedInputStreamIdentifierSerializer() {
        super(CachedInputStreamIdentifier.class);
    }

    public CachedInputStreamIdentifierSerializer(Class<CachedInputStreamIdentifier> t) {
        super(t);
    }

    @Override
    public void serialize(CachedInputStreamIdentifier value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        gen.writeStartObject();
        gen.writeStringField(FIELD_SERIALIZED_CLASS, value.getClass().getName());
        gen.writeObjectField(FIELD_SERIALIZED_VALUE, value);
        gen.writeEndObject();
    }
}
