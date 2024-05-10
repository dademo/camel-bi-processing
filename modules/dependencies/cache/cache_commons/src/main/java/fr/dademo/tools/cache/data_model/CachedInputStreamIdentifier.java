/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.tools.cache.data_model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.serializer.InputStreamIdentifierDeserializer;
import fr.dademo.tools.cache.data_model.serializer.InputStreamIdentifierSerializer;
import jakarta.annotation.Nonnull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author dademo
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CachedInputStreamIdentifier<T extends InputStreamIdentifier<?>> {

    @Nonnull
    @JsonSerialize(using = InputStreamIdentifierSerializer.class)
    @JsonDeserialize(using = InputStreamIdentifierDeserializer.class)
    private T cachedIdentifier;

    @Nonnull
    private LocalDateTime timestamp;

    @Nonnull
    private String fileName;
}
