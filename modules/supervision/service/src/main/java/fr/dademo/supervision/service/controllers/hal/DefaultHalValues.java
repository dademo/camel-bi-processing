/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.supervision.service.controllers.hal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DefaultHalValues {

    private static final TemporalAmount DEFAULT_TEMPORAL_AMOUNT = Duration.ofMinutes(15);

    public static Date getDefaultFrom() {
        return Date.from(Instant.now().minus(DEFAULT_TEMPORAL_AMOUNT));
    }

    public static Date getDefaultTo() {
        return new Date();
    }
}
