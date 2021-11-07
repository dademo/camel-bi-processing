package fr.dademo.bi.companies.repositories.file.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.repositories.file.identifier.FileIdentifier;
import fr.dademo.bi.companies.repositories.file.serializer.exception.FileIdentifierClassBodyFieldNotFoundException;
import fr.dademo.bi.companies.repositories.file.serializer.exception.FileIdentifierClassFieldNotFoundException;
import lombok.SneakyThrows;

import java.util.Optional;

import static fr.dademo.bi.companies.repositories.file.serializer.CachedFileIdentifierFields.FIELD_IDENTIFIER_CLASS_NAME;
import static fr.dademo.bi.companies.repositories.file.serializer.CachedFileIdentifierFields.FIELD_IDENTIFIER_VALUE;

public class FileIdentifierDeserializer extends JsonDeserializer<FileIdentifier<?>> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public FileIdentifier<?> deserialize(JsonParser parser, DeserializationContext ctxt) {

        return cachedFileFileIdentifierInstance(
                getFileIdentifierClassBody(parser),
                getFileIdentifierClass(parser)
        );
    }

    @SneakyThrows
    private FileIdentifier<?> cachedFileFileIdentifierInstance(String serializedBody,
                                                               Class<? extends FileIdentifier<?>> clazz) {
        return MAPPER.readValue(serializedBody, clazz);
    }

    @SneakyThrows
    private String getFileIdentifierClassBody(JsonParser parser) {
        return Optional.ofNullable(parser.getValueAsString(FIELD_IDENTIFIER_VALUE))
                .orElseThrow(FileIdentifierClassBodyFieldNotFoundException::new);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Class<? extends FileIdentifier<?>> getFileIdentifierClass(JsonParser parser) {
        return (Class<? extends FileIdentifier<?>>) Class.forName(getJsonParserClass(parser));
    }

    @SneakyThrows
    private String getJsonParserClass(JsonParser parser) {

        return Optional.ofNullable(parser.getValueAsString(FIELD_IDENTIFIER_CLASS_NAME))
                .orElseThrow(FileIdentifierClassFieldNotFoundException::new);
    }
}
