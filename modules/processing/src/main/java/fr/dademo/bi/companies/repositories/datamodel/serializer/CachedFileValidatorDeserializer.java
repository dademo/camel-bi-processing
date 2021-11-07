package fr.dademo.bi.companies.repositories.datamodel.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.dademo.bi.companies.repositories.datamodel.serializer.exception.ValidatorClassBodyFieldNotFoundException;
import fr.dademo.bi.companies.repositories.datamodel.serializer.exception.ValidatorClassFieldNotFoundException;
import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;
import lombok.SneakyThrows;

import java.util.Optional;

import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_CLASS_NAME;
import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_VALUE;

public class CachedFileValidatorDeserializer extends JsonDeserializer<CachedFileValidator<?>> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public CachedFileValidator<?> deserialize(JsonParser parser, DeserializationContext ctxt) {

        return cachedFileValidatorInstance(
                getValidatorClassBody(parser),
                getValidatorClass(parser)
        );
    }

    @SneakyThrows
    private CachedFileValidator<?> cachedFileValidatorInstance(String serializedBody,
                                                               Class<? extends CachedFileValidator<?>> clazz) {
        return MAPPER.readValue(serializedBody, clazz);
    }

    @SneakyThrows
    private String getValidatorClassBody(JsonParser parser) {
        return Optional.ofNullable(parser.getValueAsString(FIELD_VALIDATOR_VALUE))
                .orElseThrow(ValidatorClassBodyFieldNotFoundException::new);
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private Class<? extends CachedFileValidator<?>> getValidatorClass(JsonParser parser) {
        return (Class<? extends CachedFileValidator<?>>) Class.forName(getJsonParserClass(parser));
    }

    @SneakyThrows
    private String getJsonParserClass(JsonParser parser) {

        return Optional.ofNullable(parser.getValueAsString(FIELD_VALIDATOR_CLASS_NAME))
                .orElseThrow(ValidatorClassFieldNotFoundException::new);
    }
}
