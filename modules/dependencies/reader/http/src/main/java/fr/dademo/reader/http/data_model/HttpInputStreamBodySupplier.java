/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.data_model;

import jakarta.annotation.Nonnull;

import java.io.InputStream;
import java.net.http.HttpRequest;
import java.util.function.Supplier;

/**
 * @author dademo
 */
@FunctionalInterface
public interface HttpInputStreamBodySupplier {

    static HttpInputStreamBodySupplier noBody() {
        return HttpRequest.BodyPublishers::noBody;
    }

    static HttpInputStreamBodySupplier fromString(@Nonnull String s) {
        return () -> HttpRequest.BodyPublishers.ofString(s);
    }

    static HttpInputStreamBodySupplier fromByteArray(@Nonnull byte[] buf) {
        return () -> HttpRequest.BodyPublishers.ofByteArray(buf);
    }

    static HttpInputStreamBodySupplier fromByteArrays(@Nonnull Iterable<byte[]> iters) {
        return () -> HttpRequest.BodyPublishers.ofByteArrays(iters);
    }

    static HttpInputStreamBodySupplier fromString(@Nonnull InputStream in) {
        return () -> HttpRequest.BodyPublishers.ofInputStream(() -> in);
    }

    static HttpInputStreamBodySupplier fromString(@Nonnull Supplier<InputStream> streamSupplier) {
        return () -> HttpRequest.BodyPublishers.ofInputStream(streamSupplier);
    }

    @Nonnull
    HttpRequest.BodyPublisher getBodyPublisher();
}
