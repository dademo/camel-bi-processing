/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.data_model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;

import java.io.IOException;
import java.io.Serial;

import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_CLASS;
import static fr.dademo.tools.cache.data_model.serializer.Constants.FIELD_SERIALIZED_VALUE;

/**
 * @author dademo
 */
@SuppressWarnings({"java:S2055", "rawtypes", "unused"})
public class InputStreamIdentifierSerializer extends StdSerializer<InputStreamIdentifier> {

    @Serial
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
