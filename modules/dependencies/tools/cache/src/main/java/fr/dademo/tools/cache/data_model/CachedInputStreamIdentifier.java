package fr.dademo.tools.cache.data_model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.dademo.tools.cache.data_model.serializer.CachedInputStreamIdentifierDeserializer;
import fr.dademo.tools.cache.data_model.serializer.CachedInputStreamIdentifierSerializer;
import fr.dademo.tools.stream_definitions.InputStreamIdentifier;
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
    @JsonSerialize(using = CachedInputStreamIdentifierSerializer.class)
    @JsonDeserialize(using = CachedInputStreamIdentifierDeserializer.class)
    private T cachedIdentifier;

    @Nonnull
    private LocalDateTime timestamp;

    @Nonnull
    private String fileName;
}
