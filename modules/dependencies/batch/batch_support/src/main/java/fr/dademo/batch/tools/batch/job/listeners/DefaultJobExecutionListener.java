/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.batch.tools.batch.job.listeners;

import lombok.extern.slf4j.Slf4j;
import org.jline.terminal.TerminalBuilder;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author dademo
 */
@Slf4j
public class DefaultJobExecutionListener implements JobExecutionListener {

    private static final int MINIMUM_TERMINAL_WIDTH = 60;
    private final int terminalWidth;

    public DefaultJobExecutionListener() {
        terminalWidth = getTerminalWidth();
    }

    private static int getTerminalWidth() {

        try (final var term = TerminalBuilder.terminal()) {
            return Math.max(term.getWidth(), MINIMUM_TERMINAL_WIDTH);
        } catch (IOException e) {
            // Unable to get terminal width, will use a default value
            return MINIMUM_TERMINAL_WIDTH;
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        final var sbParametersFormat = new StringBuilder();
        if (jobExecution.getJobParameters().getParameters().size() == 0) {
            sbParametersFormat.append("without parameters");
        } else {
            sbParametersFormat.append("with parameters:\n");
            sbParametersFormat.append(
                jobExecution.getJobParameters()
                    .getParameters()
                    .entrySet().stream()
                    .map(es -> String.format(
                        "  - %s -> [%s] %s",
                        es.getKey(),
                        es.getValue().getType(),
                        es.getValue().toString()
                    ))
                    .collect(Collectors.joining("\n"))
            );
        }

        log.info(formatBatchInfo(String.format(
            "Batch [%s] no [%d]%n" +
                "Started on [%s]%n" +
                "%s",
            jobExecution.getJobInstance().getJobName(),
            jobExecution.getJobId(),
            Optional.ofNullable(jobExecution.getStartTime()).map(this::formatDate).orElse("-"),
            sbParametersFormat
        )));
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        final var exceptionStr = jobExecution.getAllFailureExceptions().stream()
            .map(throwable -> String.format("  - %s", throwable.getMessage()))
            .collect(Collectors.joining("\n"));

        log.info(formatBatchInfo(String.format(
            "Batch [%s] no [%d]%n" +
                "Started on [%s]%n" +
                "Ended on [%s] (%s)%n" +
                "with status [%s, %s]" +
                "%s",
            jobExecution.getJobInstance().getJobName(),
            jobExecution.getJobId(),
            Optional.ofNullable(jobExecution.getStartTime()).map(this::formatDate).orElse("-"),
            Optional.ofNullable(jobExecution.getEndTime()).map(this::formatDate).orElse("-"),
            Duration.between(
                Optional.ofNullable(jobExecution.getStartTime()).orElseGet(Date::new).toInstant(),
                Optional.ofNullable(jobExecution.getEndTime()).orElseGet(Date::new).toInstant()
            ).toString(),
            jobExecution.getExitStatus().getExitCode(),
            Optional.of(jobExecution.getExitStatus().getExitDescription()).filter(s -> !s.isBlank()).orElse("[-]"),
            (exceptionStr.length() > 0) ? "\nWith exceptions :\n" + exceptionStr : ""
        )));
    }

    @Nonnull
    protected String formatBatchInfo(@Nonnull String headerText) {
        return buildHeader(headerText, terminalWidth);
    }

    private String buildHeader(@Nonnull String headerText, int terminalWidth) {

        // We add "\n" before log in order to have a clean square
        final var sb = new StringBuilder("\n");
        final var textPre = "* ";
        final var textEnd = " *";
        final var textMaxSize = terminalWidth - textPre.length() - textEnd.length();

        // Head of the header
        IntStream.range(0, terminalWidth).mapToObj(it -> "*").forEach(sb::append);
        sb.append("\n");

        // Formatting text
        Arrays.stream(headerText.split("\n"))
            .map(this::normalizeStr)
            .flatMap(s -> stringMaxLengthAndFill(s, textMaxSize).stream())
            .map(s -> textPre + s + textEnd + "\n")
            .forEach(sb::append);

        // Tail of the header
        IntStream.range(0, terminalWidth).mapToObj(it -> "*").forEach(sb::append);

        return sb.toString();
    }

    private String normalizeStr(String s) {
        return s.replace('\t', ' ');
    }

    private List<String> stringMaxLengthAndFill(String s, int maxLength) {

        if (s.length() < maxLength) {
            return Collections.singletonList(fillWithSpace(s, maxLength));
        } else {
            final var result = new ArrayList<String>();

            for (var it = 0; it <= s.length() / maxLength; it++) {

                final var resultStr = s.substring(it * maxLength, Math.min((it + 1) * maxLength, s.length()));

                if (resultStr.length() == 0) {
                    break;
                } else if (resultStr.length() == maxLength) {
                    result.add(resultStr);
                } else {
                    result.add(fillWithSpace(resultStr, maxLength));
                }
            }

            return result;
        }
    }

    private String fillWithSpace(String s, int maxLength) {

        final var sb = new StringBuilder(maxLength);
        sb.append(s);
        IntStream.range(s.length(), maxLength)
            .forEach(ignored -> sb.append(" "));
        return sb.toString();
    }

    private String formatDate(Date date) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME
            .format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
}
