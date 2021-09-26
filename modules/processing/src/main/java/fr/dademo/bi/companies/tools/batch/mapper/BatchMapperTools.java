package fr.dademo.bi.companies.tools.batch.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BatchMapperTools {

    public static Boolean fromBoolean(@Nullable String str) {

        return Optional.ofNullable(str)
                .map(Boolean::parseBoolean)
                .orElse(null);
    }

    public static Integer fromInteger(@Nullable String str) {

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
