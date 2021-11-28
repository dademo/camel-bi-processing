/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author dademo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchMapperTools {

    public static Boolean toBoolean(@Nullable String str) {

        return Optional.ofNullable(str)
            .map(Boolean::parseBoolean)
            .orElse(null);
    }

    public static Integer toInteger(@Nullable String str) {

        return Optional.ofNullable(str)
            .map(Integer::parseInt)
            .orElse(null);
    }

    public static LocalDate toLocalDate(@Nullable String str) {

        return Optional.ofNullable(str)
            .map(DateTimeFormatter.ISO_LOCAL_DATE::parse)
            .map(LocalDate::from)
            .orElse(null);
    }

    public static LocalDateTime toLocalDateTime(@Nullable String str) {

        return Optional.ofNullable(str)
            .map(DateTimeFormatter.ISO_LOCAL_DATE_TIME::parse)
            .map(LocalDateTime::from)
            .orElse(null);
    }
}
