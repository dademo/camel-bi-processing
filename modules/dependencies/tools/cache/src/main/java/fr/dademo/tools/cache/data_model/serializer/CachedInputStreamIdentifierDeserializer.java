package fr.dademo.tools.cache.data_model.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fr.dademo.tools.cache.data_model.CachedInputStreamIdentifier;
import lombok.SneakyThrows;

import java.io.IOException;

import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_CLASS;
import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_VALUE;

@SuppressWarnings("java:S2055")
public class CachedInputStreamIdentifierDeserializer extends StdDeserializer<CachedInputStreamIdentifier<?>> {

    private static final long serialVersionUID = 8861351643710440585L;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public CachedInputStreamIdentifierDeserializer() {
        super(Object.class);
    }

    public CachedInputStreamIdentifierDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    @SuppressWarnings("unchecked")
    @SneakyThrows({ClassNotFoundException.class})
    public CachedInputStreamIdentifier<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        return MAPPER.readValue(
                p.getValueAsString(FIELD_SERIALIZED_VALUE),
                (Class<CachedInputStreamIdentifier<?>>) Class.forName(p.getValueAsString(FIELD_SERIALIZED_CLASS))
        );
    }
}
