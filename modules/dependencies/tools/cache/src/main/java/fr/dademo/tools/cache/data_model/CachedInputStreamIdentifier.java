package fr.dademo.tools.cache.data_model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import fr.dademo.tools.cache.data_model.serializer.InputStreamIdentifierDeserializer;
import fr.dademo.tools.cache.data_model.serializer.InputStreamIdentifierSerializer;
import lombok.*;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

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
