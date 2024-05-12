/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.helpers;

import jakarta.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author dademo
 */
@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class URIHelper {

    @SneakyThrows({URISyntaxException.class, MalformedURLException.class})
    public static URL updateURLWithParameters(@Nonnull URL source, @Nonnull Map<String, String> additionalParameters) {
        return updateURIWithParameters(source.toURI(), additionalParameters).toURL();
    }

    @SneakyThrows(URISyntaxException.class)
    public static URI updateURIWithParameters(@Nonnull URI source, @Nonnull Map<String, String> additionalParameters) {


        final var additionalQuery = additionalParameters.entrySet().stream()
            .map(kv -> "%s=%s".formatted(urlEncode(kv.getKey()), urlEncode(kv.getValue())))
            .collect(Collectors.joining("&"));

        final var query = Stream.of(source.getQuery(), additionalQuery)
            .filter(s -> !s.isBlank())
            .collect(Collectors.joining("&"));

        return new URI(
            source.getScheme(),
            source.getUserInfo(),
            source.getHost(),
            source.getPort(),
            source.getPath(),
            query,
            ""
        );
    }

    public static String urlEncode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
