package fr.dademo.bi.companies.repositories.datamodel.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fr.dademo.bi.companies.repositories.file.validators.CachedFileValidator;

import java.io.IOException;

import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_CLASS_NAME;
import static fr.dademo.bi.companies.repositories.datamodel.serializer.CachedFileValidatorFields.FIELD_VALIDATOR_VALUE;

public class CachedFileValidatorSerializer extends JsonSerializer<CachedFileValidator<?>> {

    @Override
    public void serialize(CachedFileValidator value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        gen.writeStartObject();
        gen.writeStringField(FIELD_VALIDATOR_CLASS_NAME, value.getClass().getCanonicalName());
        gen.writeObjectField(FIELD_VALIDATOR_VALUE, value);
        gen.writeEndObject();
    }
}
