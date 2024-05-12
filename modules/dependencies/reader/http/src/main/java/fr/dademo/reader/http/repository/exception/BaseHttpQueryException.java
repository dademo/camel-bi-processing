/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.reader.http.repository.exception;

import jakarta.annotation.Nonnull;
import lombok.Getter;

import java.io.InputStream;
import java.io.Serial;
import java.net.http.HttpResponse;

/**
 * @author dademo
 */
@Getter
public abstract class BaseHttpQueryException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5481320755074086222L;

    @Nonnull
    private final transient HttpResponse<InputStream> queryResponse;

    protected BaseHttpQueryException(@Nonnull HttpResponse<InputStream> queryResponse) {
        super();
        this.queryResponse = queryResponse;
    }

    protected BaseHttpQueryException(String message, @Nonnull HttpResponse<InputStream> queryResponse) {
        super(message);
        this.queryResponse = queryResponse;
    }

    protected BaseHttpQueryException(String message, BaseHttpQueryException cause) {
        super(message, cause);
        this.queryResponse = cause.getQueryResponse();
    }

    protected BaseHttpQueryException(String message, Throwable cause, @Nonnull HttpResponse<InputStream> queryResponse) {
        super(message, cause);
        this.queryResponse = queryResponse;
    }

    protected BaseHttpQueryException(Throwable cause, @Nonnull HttpResponse<InputStream> queryResponse) {
        super(cause);
        this.queryResponse = queryResponse;
    }
}
