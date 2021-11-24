/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.data_model;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import lombok.*;
import okhttp3.RequestBody;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URL;

/**
 * @author dademo
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class HttpInputStreamIdentifier implements InputStreamIdentifier<URL> {

    @Nonnull
    private URL url;

    @Nonnull
    @Builder.Default
    private String method = "GET";

    @Nullable
    private RequestBody requestBody;

    @Nonnull
    @Override
    public URL getSource() {
        return getUrl();
    }

    public void setSource(@Nonnull URL url) {
        this.url = url;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return String.format("Http input stream of `%s` using method `%s`", getUrl(), getMethod());
    }
}
