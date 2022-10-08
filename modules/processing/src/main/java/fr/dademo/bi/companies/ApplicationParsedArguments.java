/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.dademo.bi.companies;

import fr.dademo.bi.companies.exceptions.ApplicationInitializationError;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE, builderClassName = "ApplicationParsedArgumentsInternalBuilder")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Slf4j
public class ApplicationParsedArguments {

    private boolean help;
    private boolean force;
    private boolean listJobs;
    private List<String> only;

    public static void help(@Nonnull String messagePre) {
        log.info(messagePre + "\n" + help());
    }

    public static String help() {
        return "Jobs runner\n" +
            "Usage:\n" +
            "    " + Application.class.getName() + " [...args]\n" +
            "Arguments :\n" +
            "    --list-jobs        List the available jobs (following the configuration in the application.yml\n" +
            "    --only             Apply only certain jobs\n" +
            "    --force            Force execution (in case where jobs have already been successfully completed\n" +
            "    --help             Print this help";
    }

    public static ApplicationParsedArguments usingApplicationArguments(ApplicationArguments args) {
        return new ApplicationArgumentsBuilder(args).build();
    }

    public static class ApplicationArgumentsBuilder {

        @Nonnull
        private final ApplicationArguments applicationArguments;
        private final ApplicationParsedArguments.ApplicationParsedArgumentsInternalBuilder applicationParsedArgumentsInternalBuilder;

        public ApplicationArgumentsBuilder(@Nonnull ApplicationArguments applicationArguments) {

            this.applicationArguments = applicationArguments;
            applicationParsedArgumentsInternalBuilder = ApplicationParsedArguments.builder()
                .help(false)
                .force(false)
                .listJobs(false)
                .only(new ArrayList<>());
        }

        private void applyArguments() {

            if (!applicationArguments.getNonOptionArgs().isEmpty()) {
                throw new ApplicationInitializationError(String.format(
                    "Unknown parameters [%s]",
                    String.join(", ", applicationArguments.getNonOptionArgs())
                ));
            }

            applicationArguments.getOptionNames().forEach(this::applyArgumentByOptionName);
        }

        private void applyArgumentByOptionName(String argument) {

            switch (argument) {
                case "help":
                    applicationParsedArgumentsInternalBuilder.help(true);
                    break;
                case "list-jobs":
                    applicationParsedArgumentsInternalBuilder.listJobs(true);
                    break;
                case "only":
                    applicationParsedArgumentsInternalBuilder.only(applicationArguments.getOptionValues("only"));
                    break;
                case "force":
                    applicationParsedArgumentsInternalBuilder.force(true);
                    break;
                default:
                    throw new ApplicationInitializationError(String.format("Unknown parameter [%s]", argument));
            }
        }

        public ApplicationParsedArguments build() {

            applyArguments();
            return applicationParsedArgumentsInternalBuilder.build();
        }
    }
}
