/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.data_model;

import fr.dademo.data.generic.stream_definitions.InputStreamIdentifier;
import lombok.*;
import okhttp3.RequestBody;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode() {

        return new HashCodeBuilder(17, 41)
            .append(url)
            .append(method)
            .append(requestBody)
            .hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        final var compared = (HttpInputStreamIdentifier) obj;
        return new EqualsBuilder()
            .append(url, compared.getUrl())
            .append(method, compared.getMethod())
            .append(requestBody, compared.getRequestBody())
            .isEquals();
    }
}
