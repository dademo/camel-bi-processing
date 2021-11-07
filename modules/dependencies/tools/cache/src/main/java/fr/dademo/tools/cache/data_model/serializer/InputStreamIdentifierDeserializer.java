package fr.dademo.tools.cache.data_model.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.tools.DefaultToolBeans;
import lombok.SneakyThrows;

import java.io.IOException;

import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_CLASS;
import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_VALUE;

@SuppressWarnings("java:S2055")
public class InputStreamIdentifierDeserializer extends StdDeserializer<InputStreamIdentifier<?>> {

    private static final long serialVersionUID = 8861351643710440585L;

    public InputStreamIdentifierDeserializer() {
        super(Object.class);
    }

    public InputStreamIdentifierDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    @SneakyThrows({ClassNotFoundException.class})
    public InputStreamIdentifier<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        final var defaultObjectMapper = new DefaultToolBeans().defaultObjectMapper();

        final var treeNode = p.readValueAsTree();

        final var valueClass = defaultObjectMapper.readValue(treeNode.get(FIELD_SERIALIZED_CLASS).toString(), String.class);

        return (InputStreamIdentifier<?>) defaultObjectMapper.readValue(
                treeNode.get(FIELD_SERIALIZED_VALUE).toString(),
                Class.forName(valueClass)
        );
    }
}
