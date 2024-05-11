/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.data.definitions.data_gouv_fr.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import fr.dademo.data.definitions.data_gouv_fr.serializer.exception.LocalDateTimeParseException;

import java.io.IOException;
import java.io.Serial;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author dademo
 */
// @Slf4j
@SuppressWarnings("java:S2055")
public class ApifrDatesDeserializer extends StdDeserializer<LocalDateTime> {

    public static final DateTimeFormatter DATE_FORMAT_WITH_MICROSECONDS = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nnnnnnZZZZZ");
    public static final DateTimeFormatter DATE_FORMAT_SIMPLE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
    @Serial
    private static final long serialVersionUID = 4036858873660699102L;

    public ApifrDatesDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

        final var value = p.readValueAs(String.class);
        return Stream.of(DATE_FORMAT_WITH_MICROSECONDS, DATE_FORMAT_SIMPLE)
            .map(formatter -> tryDeserialize(formatter, value))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst()
            .orElseThrow(() -> new LocalDateTimeParseException(value));
    }

    private Optional<LocalDateTime> tryDeserialize(DateTimeFormatter formatter, String value) {

        try {
            return Optional.of(LocalDateTime.parse(value, formatter));
        } catch (DateTimeParseException ex) {
            return Optional.empty();
        }
    }
}
